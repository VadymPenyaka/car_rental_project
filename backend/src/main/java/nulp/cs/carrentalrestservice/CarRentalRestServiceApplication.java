package nulp.cs.carrentalrestservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.context.annotation.Bean;

import static org.yaml.snakeyaml.nodes.Tag.STR;

@SpringBootApplication
public class CarRentalRestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarRentalRestServiceApplication.class, args);
	}


//	@Bean
//	CommandLineRunner commandLineRunner (JdbcConnectionDetails jdbc) {
//		return args-> {
//			var details = STR."""
//     				class: \{jdbc.getClass().getName()}
//     				JDBC URL: \{jdbc.getJdbcUrl()}
//     				Username: \{jdbc.getUsername()}
//     				Password: \{jdbc.getPassword()}
//					""";
//		};
//
//	}
}
