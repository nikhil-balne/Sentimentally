package com.main.sentimentally;

import com.main.sentimentally.config.DotenvPropertyInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SentimentallyApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SentimentallyApplication.class);
		app.addInitializers(new DotenvPropertyInitializer()); // Register dotenv
																// initializer
		app.run(args);
	}

}
