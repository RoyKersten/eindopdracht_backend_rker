package nl.novi.autogarage_roy_kersten;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication                                    //consists of: @Configuration, @ComponentScan, @EnableAutoConfiguration)
public class AutogarageRoyKerstenApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutogarageRoyKerstenApplication.class, args);
     }

}
