package whz.pti.eva;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GradeCalculatorTekariChalehAljarrahApplication {

	public static void main(String[] args) {
		SpringApplication.run(GradeCalculatorTekariChalehAljarrahApplication.class, args);
	}

	@Bean
	CommandLineRunner init() {
		return (evt) -> System.out.println("Hallo Welt");
	}

}
 