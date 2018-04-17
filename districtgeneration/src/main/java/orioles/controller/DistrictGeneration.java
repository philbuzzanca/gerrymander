package orioles.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan({"orioles.model"})
@EnableJpaRepositories("orioles.persistence")
public class DistrictGeneration {
	public static void main(String[] args) {
		SpringApplication.run(DistrictGeneration.class, args);
	}
}
