package eu.omegasoftware.ugovor;

import eu.omegasoftware.artikl.ArtiklDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UgovorMapper {
	UgovorListElementDto toUgovorListElementDto(Ugovor ugovor);

	@Mapping(target = "artikli", source = "ugovorArtikli")
	UgovorDetailDto toUgovorDetailDto(Ugovor ugovor);

	@Mapping(target = "naziv", source = "artikl.naziv")
	@Mapping(target = "dobavljac", source = "artikl.dobavljac")
	ArtiklDto toArtiklDto(UgovorArtikl ugovorArtikl);
}
