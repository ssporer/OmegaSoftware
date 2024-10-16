package eu.omegasoftware.ugovor;

import eu.omegasoftware.artikl.ArtiklDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UgovorDetailDto {

	private Long id;

	private String kupac;

	private String brojUgovora;

	private LocalDate datumAkontacije;

	private LocalDate rokIsporuke;

	private Status status;

	private List<ArtiklDto> artikli;

}
