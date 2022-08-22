package shop.dalda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DaldaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaldaApplication.class, args);
	}

}
