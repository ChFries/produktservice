package prv.fries.produktservice.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import prv.fries.produktservice.generated.ProduktVerfuegbarDto;

@Service
@RequiredArgsConstructor
public class RabbitMQPublisher {

    private static final String ROUTING_KEY = "bestellung.pruefung.abgeschlossen";

    private final RabbitTemplate rabbitTemplate;

    public void publishPruefungAbgeschlossen(ProduktVerfuegbarDto verfuegbareProdukte) {
        rabbitTemplate.convertAndSend(ROUTING_KEY, verfuegbareProdukte);
    }
}