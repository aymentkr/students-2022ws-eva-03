package whz.pti.eva;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import whz.pti.eva.common.InitializeService;

/**
 * The type Pizza projekt tekari chaleh aljarrah application.
 */
@SpringBootApplication
public class PizzaProjektTekariChalehAljarrahApplication {

	/** The initialize service. */
	@Autowired
	private InitializeService initializeService;

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(PizzaProjektTekariChalehAljarrahApplication.class, args);
	}

	/**
	 * Fills some Data in the database.
	 *
	 * @return the command line runner
	 */
	@Bean
	public CommandLineRunner init() {
		return (evt) -> {
			initializeService.initDatabase();
		};
	}
}
