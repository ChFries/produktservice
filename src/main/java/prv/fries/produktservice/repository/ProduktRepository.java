package prv.fries.produktservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prv.fries.produktservice.entity.Produkt;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProduktRepository extends JpaRepository<Produkt, UUID> {

    Optional<Produkt> findById(UUID uuid);

}
