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
        Produkt produkt = produktRepository.findById(position.getProduktId())
                .orElseGet(() -> {
                    Produkt neu = new Produkt();
                    neu.setName("");
                    neu.setPreis(position.getEinzelpreis());
                    neu.setLagerbestand(BigDecimal.ZERO);
                    return neu;
                });

//        BigDecimal neueMenge = getLagerMenge(produkt.getLagerbestand(), position.getMenge());
        BigDecimal neueMenge = BigDecimal.valueOf(position.getMenge());
        produkt.setLagerbestand(neueMenge);

        produktRepository.save(produkt);
        position.setVerfuegbar(Boolean.TRUE);
    }

//    private void createOrUpdateProduct(BestellPositionDto position) {
//        position.setVerfuegbar(Boolean.TRUE);
//    }


    private BigDecimal getLagerMenge(BigDecimal lagerMenge, Integer positionsMenge) {
        return lagerMenge.compareTo(BigDecimal.valueOf(positionsMenge)) == -1 ? BigDecimal.valueOf(positionsMenge) : lagerMenge ;
    }

}
