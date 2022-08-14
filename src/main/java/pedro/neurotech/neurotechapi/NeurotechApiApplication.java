package pedro.neurotech.neurotechapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class NeurotechApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NeurotechApiApplication.class, args);

	}

}
