package eu.omegasoftware.ugovor;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UgovorListElementDto {

	private Long id;

	private String kupac;

	private String brojUgovora;

	private LocalDate datumAkontacije;

	private LocalDate rokIsporuke;

	private Status status;
}
