package prv.fries.produktservice.rest;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.ProduktApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import prv.fries.produktservice.generated.ProduktDto;
import prv.fries.produktservice.generated.ProduktVerfuegbarDto;
import prv.fries.produktservice.generated.ProduktVerfuegbarkeitDto;
import prv.fries.produktservice.service.ProduktService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
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
    public ResponseEntity<List<ProduktVerfuegbarDto>> pruefeVerfuegbarkeit( @Valid @RequestBody List<@Valid ProduktVerfuegbarkeitDto> produktVerfuegbarkeitDto) {
        List<ProduktVerfuegbarDto> uberpruefteProdukte = produktService.pruefeVerfuegbarkeit(produktVerfuegbarkeitDto);
        return ResponseEntity.ok(uberpruefteProdukte);
    }



}
