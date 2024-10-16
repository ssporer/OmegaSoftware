package eu.omegasoftware.ugovor;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/ugovor")
public class UgovorController {

	@Autowired
	private UgovorService service;

	@Autowired
	private UgovorMapper mapper;


	@GetMapping
	public List<UgovorListElementDto> getUgovorList(@RequestParam(required = false) String ime,
			@RequestParam(required =
					false) Status aktivnost) {
		return service.findAll(ime, aktivnost)
					  .stream()
					  .map(mapper::toUgovorListElementDto)
					  .collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public UgovorDetailDto getUgovor(@PathVariable Long id) {
		return mapper.toUgovorDetailDto(service.get(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createUgovor(@RequestBody @Valid Ugovor ugovor) {
		log.info("Creating ugovor for {}...", ugovor.getKupac());
		service.create(ugovor);
	}

	@PutMapping("/{id}")
	public void updateUgovor(@PathVariable Long id, @RequestBody @Valid Ugovor ugovor) {
		log.info("Updating ugovor {}...", id);
		service.update(id, ugovor);
	}

	@PostMapping("/{id}/confirm")
	public void confirmUgovor(@PathVariable Long id) {
		log.info("Confirming ugovor {}...", id);
		service.confirm(id);
	}

	@DeleteMapping("/{id}")
	public void deleteUgovor(@PathVariable Long id) {
		log.info("Deleting ugovor {}...", id);
		service.delete(id);
	}

}
