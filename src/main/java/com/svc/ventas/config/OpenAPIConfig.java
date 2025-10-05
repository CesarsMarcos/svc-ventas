package com.svc.ventas.config;

import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

public class OpenAPIConfig {

	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI()
				/*.components(new Components().addSecuritySchemes("spring_oauth",
						new SecurityScheme().type(SecurityScheme.Type.OAUTH2).description("Oauth2 flow")
								.flows(new OAuthFlows()
										.clientCredentials(new OAuthFlow().tokenUrl("http://localhost:1200/oauth/token")
												.scopes(new Scopes().addString("read", "for read operations")
														.addString("write", "for write operations"))))))
				.security(Arrays.asList(new SecurityRequirement().addList("spring_oauth")))*/
				.info(new Info().title("Book Application API")
						.description("This is a sample Spring Boot RESTful service using springdoc-openapi and OpenAPI 3.")
						.termsOfService("terms")
						.contact(new Contact().email("codersitedev@gmail.com").name("Developer: Moises Gamio"))
						.license(new License().name("GNU")).version("2.0"));
	}
	
}
