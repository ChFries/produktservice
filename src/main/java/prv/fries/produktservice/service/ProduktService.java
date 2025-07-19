package prv.fries.produktservice.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import prv.fries.produktservice.entity.Produkt;
import prv.fries.produktservice.generated.ProduktVerfuegbarDto;
import prv.fries.produktservice.generated.ProduktVerfuegbarkeitDto;
import prv.fries.produktservice.mapper.ProduktMapper;
import prv.fries.produktservice.repository.ProduktRepository;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class ProduktService {

    private final ProduktRepository produktRepository;

    private final ProduktMapper produktMapper;

    @Transactional
    public List<ProduktVerfuegbarDto> pruefeVerfuegbarkeit(List<ProduktVerfuegbarkeitDto> produktVerfuegbarkeitDtoList){
        return produktVerfuegbarkeitDtoList.stream().map(this::createIfNotExists).toList();
    }

    //Legt Produkt an oder setzt Menge - keine echte Prüfung ob Verfügbarkeit, lediglich Lasterzeugung
    private ProduktVerfuegbarDto createIfNotExists(ProduktVerfuegbarkeitDto dto) {
        var produkt = produktRepository.findById(dto.getProduktId());

        var updatedOrCreatedProduct = produkt.map(product -> updateExistingProduct(product, dto)).orElse(produktMapper.pdvDTOtoEntity(dto));

        produktRepository.save(updatedOrCreatedProduct);

        ProduktVerfuegbarDto returnDto = new ProduktVerfuegbarDto();
        returnDto.setProduktId(dto.getProduktId());
        returnDto.setVerfuegbar(true);

        return returnDto;
    }

    private Produkt updateExistingProduct(Produkt produkt, ProduktVerfuegbarkeitDto produktVerfuegbarDto) {
        log.info("Updating existing product with UUID {}", produkt.getId());
        if(produktVerfuegbarDto.getMenge().compareTo(produkt.getLagerbestand()) > 0){
            produkt.setLagerbestand(produktVerfuegbarDto.getMenge());
        }
        return produkt;
    }


}
