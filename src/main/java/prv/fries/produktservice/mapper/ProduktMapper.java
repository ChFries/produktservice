package prv.fries.produktservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import prv.fries.produktservice.entity.Produkt;
import prv.fries.produktservice.generated.ProduktVerfuegbarkeitDto;


@Mapper(componentModel = "spring")
public interface ProduktMapper {

    @Mapping(target = "id", source = "produktId")
    @Mapping(target = "lagerbestand", source = "menge")
    @Mapping(target = "preis", source ="preis")
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "beschreibung", ignore = true)
    Produkt pdvDTOtoEntity(ProduktVerfuegbarkeitDto produktVerfuegbarkeitDto);

}
