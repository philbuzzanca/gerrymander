package orioles.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"orioles.persistence"})
public class DistrictGeneration {
	public static void main(String[] args) {
		SpringApplication.run(DistrictGeneration.class, args);
	}
}
