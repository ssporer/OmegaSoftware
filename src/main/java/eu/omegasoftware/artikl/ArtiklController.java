package eu.omegasoftware.artikl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/artikl")
public class ArtiklController {

	@Autowired
	private ArtiklRepository artiklRepository;

	@GetMapping
	public List<Artikl> getArtikl() {
		return artiklRepository.findAll();
	}
}
