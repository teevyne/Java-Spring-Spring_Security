package com.iwiseoauth.springbootoauthjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
public class SpringBootOauthJwtApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringBootOauthJwtApplication.class, args);
	}
}
