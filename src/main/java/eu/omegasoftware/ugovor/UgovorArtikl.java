package eu.omegasoftware.ugovor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.omegasoftware.artikl.Artikl;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ugovor_artikl")
public class UgovorArtikl {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "ugovor_id")
	private Ugovor ugovor;

	@Valid
	@NotNull
	@ManyToOne
	@JoinColumn(name = "artikl_id")
	private Artikl artikl;

	@NotNull
	private Integer kolicina;
}
