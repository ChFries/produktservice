package prv.fries.produktservice.rest;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.ProduktApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import prv.fries.produktservice.generated.BestellungDto;
import prv.fries.produktservice.generated.ProduktDto;
import prv.fries.produktservice.service.ProduktService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProduktController implements ProduktApi {

    private final ProduktService produktService;

    @Override
    public ResponseEntity<List<ProduktDto>> getAlleProdukte() {
        return null;
    }

    @Override
    public ResponseEntity<ProduktDto> getProduktById(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<BestellungDto> pruefeVerfuegbarkeit(BestellungDto bestellungDto) {
        log.info("[REST] Uberpruefe Verfuegbarkeit von Positionen der Bestellung {}", bestellungDto.getId());
        var response = produktService.ueberpruefeVerfuegbarkeit(bestellungDto);
        return ResponseEntity.ok(response);
    }
}
