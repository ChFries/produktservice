package prv.fries.produktservice.rest;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.ProduktApi;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import prv.fries.produktservice.generated.BestellungDto;
import prv.fries.produktservice.service.ProduktService;


@RestController
@RequiredArgsConstructor
@Slf4j
@Profile("REST")
public class ProduktController implements ProduktApi {

    private final ProduktService produktService;

    @Override
    public ResponseEntity<BestellungDto> pruefeVerfuegbarkeit(BestellungDto bestellungDto) {
        log.info("[REST] Uberpruefe Verfuegbarkeit von Positionen der Bestellung {}", bestellungDto.getId());
        var response = produktService.ueberpruefeVerfuegbarkeit(bestellungDto);
        return ResponseEntity.ok(response);
    }
}
