package com.svc.ventas;

import com.svc.ventas.config.SecurityConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@Log4j2
@RequiredArgsConstructor
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableFeignClients
public class SvcVentasApplication implements CommandLineRunner {

	private final SecurityConfig passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SvcVentasApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String clave = passwordEncoder.passwordEncoder().encode("12345");
		log.info("clave {}", clave);
	}
}
