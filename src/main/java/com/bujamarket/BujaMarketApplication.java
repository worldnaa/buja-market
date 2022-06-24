package com.bujamarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class BujaMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(BujaMarketApplication.class, args);
	}

}
