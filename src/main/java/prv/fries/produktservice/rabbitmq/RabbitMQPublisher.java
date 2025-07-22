package prv.fries.produktservice.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import prv.fries.produktservice.generated.ProduktVerfuegbarDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQPublisher {

    private static final String ROUTING_KEY = "bestellung.pruefung.abgeschlossen";

    private final RabbitTemplate rabbitTemplate;

    public void publishPruefungAbgeschlossen(ProduktVerfuegbarDto verfuegbareProdukte) {
        log.info("Pruefung abgeschlossen für {}", verfuegbareProdukte.getBestellId());
        rabbitTemplate.convertAndSend(ROUTING_KEY, verfuegbareProdukte);
    }
}