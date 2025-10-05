package com.svc.ventas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SvcVentasApplication /*implements CommandLineRunner*/{

	public static void main(String[] args) {
		SpringApplication.run(SvcVentasApplication.class, args);
	}

}
