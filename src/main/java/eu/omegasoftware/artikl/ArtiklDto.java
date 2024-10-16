package eu.omegasoftware.artikl;

import lombok.Data;

@Data
public class ArtiklDto {

	private Long id;

	private String naziv;

	private String dobavljac;

	private Integer kolicina;

}
