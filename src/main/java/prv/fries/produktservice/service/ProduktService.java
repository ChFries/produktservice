package prv.fries.produktservice.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import prv.fries.produktservice.entity.Produkt;
import prv.fries.produktservice.generated.AbgefragtePositionen;
import prv.fries.produktservice.generated.UeberprueftePositionen;
import prv.fries.produktservice.generated.client.payment.BestellPositionDto;
import prv.fries.produktservice.generated.client.payment.BestellungDto;
import prv.fries.produktservice.mapper.ProduktMapper;
import prv.fries.produktservice.repository.ProduktRepository;

import java.math.BigDecimal;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class ProduktService {

    private final ProduktRepository produktRepository;

    private final ProduktMapper produktMapper;

    @Transactional
    public List<UeberprueftePositionen> pruefeVerfuegbarkeit(List<AbgefragtePositionen> produktVerfuegbarkeitDtoList){
        return produktVerfuegbarkeitDtoList.stream().map(this::createIfNotExists).toList();
    }

    //Legt Produkt an oder setzt Menge - keine echte Prüfung ob Verfügbarkeit, lediglich Lasterzeugung
    private UeberprueftePositionen createIfNotExists(AbgefragtePositionen dto) {
        var produkt = produktRepository.findById(dto.getProduktId());

        var updatedOrCreatedProduct = produkt.map(product -> updateExistingProduct(product, dto)).orElse(produktMapper.pdvDTOtoEntity(dto));

        produktRepository.save(updatedOrCreatedProduct);

        UeberprueftePositionen returnDto = new UeberprueftePositionen();
        returnDto.setProduktId(dto.getProduktId());
        returnDto.setVerfuegbar(true);

        return returnDto;
    }

    public BestellungDto ueberpruefeVerfuegbarkeit(BestellungDto dto) {
        dto.getBestellPositionen().forEach(this::createOrUpdateProduct);
        return dto;
    }

    private void createOrUpdateProduct(BestellPositionDto position) {
        var produkt = produktRepository.findById(position.getProduktId());
        produkt.ifPresentOrElse(
                prod -> {
                    var lagerMenge = getLagerMenge(prod.getLagerbestand(), position.getMenge());
                    prod.setLagerbestand(lagerMenge);
                    produktRepository.save(prod);
                },
                () -> {
                    var prod = new Produkt();
                    prod.setLagerbestand(BigDecimal.valueOf(position.getMenge()));
                    prod.setName("");
                    prod.setPreis(position.getEinzelpreis());
                    produktRepository.save(prod);
                }
        );
        position.setVerfuegbar(Boolean.TRUE);
    }

    private BigDecimal getLagerMenge(BigDecimal lagerMenge, Integer positionsMenge) {
        return lagerMenge.compareTo(BigDecimal.valueOf(positionsMenge)) == -1 ? BigDecimal.valueOf(positionsMenge) : lagerMenge ;
    }

    private Produkt updateExistingProduct(Produkt produkt, AbgefragtePositionen produktVerfuegbarDto) {
        log.info("Updating existing product with UUID {}", produkt.getId());
        if(produktVerfuegbarDto.getMenge().compareTo(produkt.getLagerbestand()) > 0){
            produkt.setLagerbestand(produktVerfuegbarDto.getMenge());
        }
        return produkt;
    }


}
