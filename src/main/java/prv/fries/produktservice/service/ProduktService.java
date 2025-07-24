package prv.fries.produktservice.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prv.fries.produktservice.entity.Produkt;
import prv.fries.produktservice.generated.BestellPositionDto;
import prv.fries.produktservice.generated.BestellungDto;
import prv.fries.produktservice.repository.ProduktRepository;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class ProduktService {

    private final ProduktRepository produktRepository;


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

}
