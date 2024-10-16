package eu.omegasoftware.ugovor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class UgovorDao {

	private static final ExampleMatcher UGOVOR_MATCHER = ExampleMatcher.matching()
																	   .withMatcher(
																			   "kupac",
																			   ExampleMatcher.GenericPropertyMatchers.contains()
																													 .ignoreCase()
																	   );

	@Autowired
	private UgovorRepository repository;

	public List<Ugovor> findAll(String ime, Status aktivnost) {
		Ugovor probe = new Ugovor();
		probe.setKupac(ime);
		probe.setStatus(aktivnost);
		return repository.findAll(Example.of(probe, UGOVOR_MATCHER));
	}

	public Ugovor get(Long id) {
		return repository.findById(id)
						 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public void save(Ugovor ugovor) {
		repository.save(ugovor);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	public Optional<String> getLatestBrojUgovora() {
		return repository.findFirstByBrojUgovoraContainsOrderByBrojUgovora("/" + LocalDate.now()
																						  .getYear())
						 .map(Ugovor::getBrojUgovora);
	}
}
