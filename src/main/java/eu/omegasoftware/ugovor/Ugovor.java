package eu.omegasoftware.ugovor;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "active")
@SQLDelete(sql = "UPDATE ugovor SET active = false WHERE id = ?")
public class Ugovor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String kupac;

	private String brojUgovora;

	@NotNull
	private LocalDate datumAkontacije;

	@NotNull
	private LocalDate rokIsporuke;

	@Enumerated(EnumType.STRING)
	private Status status;

	private Boolean active = true;

	@Valid
	@OneToMany(mappedBy = "ugovor", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UgovorArtikl> ugovorArtikli = new LinkedHashSet<>();

	@SuppressWarnings("unused")
	public void setUgovorArtikli(Set<UgovorArtikl> ugovorArtikli) {
		this.ugovorArtikli = ugovorArtikli;
		if (ugovorArtikli != null) {
			ugovorArtikli.forEach(ugovorArtikl -> ugovorArtikl.setUgovor(this));
		}
	}
}
