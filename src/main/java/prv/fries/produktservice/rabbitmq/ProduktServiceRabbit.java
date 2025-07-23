package prv.fries.produktservice.rabbitmq;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import prv.fries.produktservice.generated.BestellungDto;
import prv.fries.produktservice.service.ProduktService;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProduktServiceRabbit {

    private final ProduktService produktService;

    private final RabbitMQPublisher rabbitMQPublisher;

    @RabbitListener(queues = RabbitMQListenerConfiguration.QUEUE_NAME)
    public void handleBestellungAngelegt(BestellungDto dto) {
        produktService.ueberpruefeVerfuegbarkeit(dto);
        rabbitMQPublisher.publishPruefungAbgeschlossen(dto);
    }

}
