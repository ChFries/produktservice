package prv.fries.produktservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
public class Produkt {

    @Id
    private UUID id;

    @Column
    private String name;

    @Column(length = 1000)
    private String beschreibung;

    @Column(nullable = false)
    private float preis;

    @Column(nullable = false)
    private BigDecimal lagerbestand;

}
