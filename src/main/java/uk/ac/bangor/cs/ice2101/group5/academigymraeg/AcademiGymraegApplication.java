package uk.ac.bangor.cs.ice2101.group5.academigymraeg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class AcademiGymraegApplication{

	public static void main(String[] args) {
		SpringApplication.run(AcademiGymraegApplication.class, args);
	}

}
