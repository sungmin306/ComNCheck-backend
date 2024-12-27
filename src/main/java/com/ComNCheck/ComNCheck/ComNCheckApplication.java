package com.ComNCheck.ComNCheck;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ComNCheckApplication {

	public static void main(String[] args) {

		//Load .env file
		Dotenv dotenv = Dotenv.load();
		System.setProperty("H2_DB_URL", dotenv.get("H2_DB_URL"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
		SpringApplication.run(ComNCheckApplication.class, args);

	}

}
