package eu.omegasoftware.ugovor;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static eu.omegasoftware.ugovor.Status.KREIRANO;
import static eu.omegasoftware.ugovor.UgovorValidator.validateDelete;
import static eu.omegasoftware.ugovor.UgovorValidator.validateRokIsporukechange;

@Slf4j
@Service
public class UgovorService {

	@Autowired
	private UgovorDao dao;

	public List<Ugovor> findAll(String ime, Status aktivnost) {
		return dao.findAll(ime, aktivnost);
	}

	public Ugovor get(Long id) {
		return dao.get(id);
	}

	/**
	 * Creates a new Ugovor instance, assigns it a new unique identifier and contract number,
	 * and sets its status to "KREIRANO".
	 * Broj ugovora se kreira iz dva dijela, prvi je sekvenca rednih
	 * brojeva, a drugi je godina u kojoj je ugovor kreiran.
	 *
	 * @param ugovor The Ugovor object to be created and persisted.
	 */
	public void create(Ugovor ugovor) {
		ugovor.setId(null);
		ugovor.setStatus(KREIRANO);

		// Izracunaj novi broj ugovora
		int zadnjiRedniBroj = Integer.parseInt(dao.getLatestBrojUgovora()
												  .map(s -> s.split("/")[0])
												  .orElse("0"));
		String brojUgovora = "%02d/%d".formatted(
				++zadnjiRedniBroj,
				LocalDate.now()
						 .getYear()
		);
		log.info("Generating new broj ugovora: {}", brojUgovora);
		ugovor.setBrojUgovora(brojUgovora);
		dao.save(ugovor);
	}

	/**
	 * Updates an existing Ugovor entry in the database with new values.
	 * The status and contract number cannot be changed during the update process.
	 * Validates the change of the delivery deadline (rok isporuke).
	 *
	 * @param id     The unique identifier of the Ugovor to update.
	 * @param ugovor The Ugovor object containing updated values.
	 */
	public void update(Long id, Ugovor ugovor) {
		Ugovor dbUgovor = dao.get(id);
		// Status nije moguce mijenjati sa update
		ugovor.setStatus(dbUgovor.getStatus());
		// Broj ugovora je autogeneriran i nije dopustane promjena
		ugovor.setBrojUgovora(dbUgovor.getBrojUgovora());

		validateRokIsporukechange(dbUgovor, ugovor);

		ugovor.setId(id);
		dao.save(ugovor);
	}

	/**
	 * Confirms the Ugovor by changing its status to the next state in the transition.
	 *
	 * @param id the unique identifier of the Ugovor to confirm
	 */
	@Transactional
	public void confirm(Long id) {
		Ugovor dbUgovor  = dao.get(id);
		Status oldStatus = dbUgovor.getStatus();
		Status newStatus = Status.transition(oldStatus);
		dbUgovor.setStatus(newStatus);
		log.info("Status {} changed to -> {}", oldStatus, newStatus);
	}

	/**
	 * Deletes a Ugovor by its unique identifier.
	 * This method first validates whether the Ugovor can be deleted
	 * by checking its current status. Only Ugovori in the status "KREIRANO" are allowed to be deleted.
	 *
	 * @param id The unique identifier of the Ugovor to be deleted.
	 */
	public void delete(Long id) {
		validateDelete(dao.get(id));
		dao.delete(id);
	}

}
