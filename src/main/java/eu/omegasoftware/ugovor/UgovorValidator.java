package eu.omegasoftware.ugovor;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

import static eu.omegasoftware.ugovor.Status.KREIRANO;
import static eu.omegasoftware.ugovor.Status.NARUCENO;

public class UgovorValidator {

	private static final Set<Status> AKTIVNI_STATUSI = Set.of(KREIRANO, NARUCENO);

	/**
	 * Rok isporuke je moguće mijenjati
	 * samo aktivnom kupoprodajnom ugovoru.
	 * Aktivni ugovori su oni koji su u statusu
	 * „KREIRANO“ ili „NARUČENO“,
	 *
	 * @param dbUgovor - ugovor from database
	 * @param ugovor   - ugovor from request
	 * @throws ResponseStatusException - in case rokIsporuke was changed on inactive ugovor
	 */
	public static void validateRokIsporukechange(Ugovor dbUgovor, Ugovor ugovor) {
		// Nema promjene
		if (dbUgovor.getRokIsporuke() == ugovor.getRokIsporuke()) return;

		// Ako ugovor nije aktivan, nije dozvoljena promjena roka isporuke
		if (!AKTIVNI_STATUSI.contains(ugovor.getStatus())) throw new ResponseStatusException(
				HttpStatus.UNPROCESSABLE_ENTITY,
				"Nije moguće poromijeniti rok isporuke jer ugovor nije u aktivnom stanju!"
		);
	}

	/**
	 * Moguće je brisati samo kupoprodajne
	 * ugovore koji su u statusu „KREIRANO“.
	 *
	 * @param ugovor - ugovor for deletion
	 */
	public static void validateDelete(Ugovor ugovor) {
		if (ugovor.getStatus() != KREIRANO) throw new ResponseStatusException(
				HttpStatus.UNPROCESSABLE_ENTITY,
				"Nije dozvoljeno brisanje ugovora koje je u statusu %s!".formatted(ugovor.getStatus())
		);
	}

}
