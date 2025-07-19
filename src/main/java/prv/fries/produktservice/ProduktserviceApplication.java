package prv.fries.produktservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ProduktserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProduktserviceApplication.class, args);
    }

}
