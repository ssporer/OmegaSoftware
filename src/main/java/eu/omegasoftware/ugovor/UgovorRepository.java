package eu.omegasoftware.ugovor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UgovorRepository extends JpaRepository<Ugovor, Long> {
	Optional<Ugovor> findFirstByBrojUgovoraContainsOrderByBrojUgovora(String godina);
}
