package orioles.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("orioles.persistence")
public class DistrictGenerationApplication {
	public static void main(String[] args) {
		SpringApplication.run(DistrictGenerationApplication.class, args);
	}
}
