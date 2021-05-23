package nl.novi.autogarage_roy_kersten;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication  //(exclude = { SecurityAutoConfiguration.class })   // DISABLE SECURITY
public class AutogarageRoyKerstenApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutogarageRoyKerstenApplication.class, args);
     }

}
