package prv.fries.produktservice.rabbitmq;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
@Slf4j
public class RabbitMQListenerConfiguration {

    public static final String QUEUE_NAME = "bestellung.bestellung.angelegt";
    public static final String ROUTING_KEY = "bestellung.angelegt";



    @Bean
    public Queue bestellungAngelegtQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public Binding bestellungAngelegtBinding(Queue bestellungAngelegtQueue, RabbitMQGeneralConfig config) {
        return BindingBuilder
                .bind(bestellungAngelegtQueue)
                .to(config.exchange())
                .with(ROUTING_KEY);
    }

}