package eu.omegasoftware.ugovor;

import org.springframework.lang.NonNull;

public enum Status {

	KREIRANO,
	NARUCENO,
	ISPORUCENO;

	public static Status transition(@NonNull Status status) {
		if (KREIRANO.equals(status)) return NARUCENO;
		return ISPORUCENO;
	}
}
