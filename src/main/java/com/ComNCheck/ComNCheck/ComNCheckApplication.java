package com.ComNCheck.ComNCheck;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ComNCheckApplication {

	public static void main(String[] args) {

		System.out.println("실행");
		//Load .env file
		Dotenv dotenv = Dotenv.load();
		System.setProperty("H2_DB_URL", dotenv.get("H2_DB_URL"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
		System.setProperty("GOOGLE_CLIENT_ID", dotenv.get("GOOGLE_CLIENT_ID"));
		System.setProperty("GOOGLE_CLIENT_SECRET", dotenv.get("GOOGLE_CLIENT_SECRET"));
		System.setProperty("JWT_EXPIRATIONMS",dotenv.get("JWT_EXPIRATIONMS"));
		System.setProperty("GOOGLE_REDIRECT_URI",dotenv.get("GOOGLE_REDIRECT_URI"));
		SpringApplication.run(ComNCheckApplication.class, args);
	}

}
