package eu.omegasoftware.ugovor;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.omegasoftware.artikl.Artikl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Set;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UgovorController.class)
class UgovorControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UgovorService service;

	@MockBean
	@SuppressWarnings("unused")
	private UgovorMapper ugovorMapper;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void createUgovor_validInput_createsUgovor() throws Exception {
		// Create a sample Ugovor object
		Ugovor ugovor = new Ugovor();
		ugovor.setKupac("ime kupca");
		ugovor.setDatumAkontacije(LocalDate.now());
		ugovor.setRokIsporuke(LocalDate.now());

		UgovorArtikl ugovorArtikl = new UgovorArtikl();
		Artikl       artikl       = new Artikl();
		artikl.setId(1L);
		ugovorArtikl.setArtikl(artikl);
		ugovorArtikl.setKolicina(1);
		ugovor.setUgovorArtikli(Set.of(ugovorArtikl));

		// Configure the mock behavior
		doNothing().when(service)
				   .create(ugovor);

		// Perform the request and check the response
		mockMvc.perform(
					   MockMvcRequestBuilders.post("/ugovor")
											 .contentType(MediaType.APPLICATION_JSON)
											 .content(objectMapper.writeValueAsString(ugovor))
			   )
			   .andExpect(status().isCreated());
	}

	@Test
	public void createUgovor_invalidInput_returnsBadRequest() throws Exception {
		// Create an invalid Ugovor object
		Ugovor ugovor = new Ugovor();

		// Perform the request and check the response
		mockMvc.perform(
					   MockMvcRequestBuilders.post("/ugovor")       // Replace /url-mapping with actual URL
											 .contentType(MediaType.APPLICATION_JSON)
											 .content(objectMapper.writeValueAsString(ugovor))
			   )
			   .andExpect(status().isBadRequest());
	}
}
