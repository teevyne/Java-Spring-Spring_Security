package com.iwiseoauth.springbootoauthjwt.config;

import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final String[] PERMIT_USER_GET_REQUEST = { "/home", "/user/{username}"};


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(request -> {
            var cors = new CorsConfiguration();
            cors.setAllowedOrigins(List.of("http://localhost:8080"));
            cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            cors.setAllowedHeaders(List.of("*"));
            return cors;
        }).and().authorizeRequests().antMatchers("/api-docs/**","/configuration/**", "/swagger-ui/**", "/swagger-resources/**","/swagger-ui.html/",
                "/webjars/**", "/api-docs", "/api-docs/**", "/v2/api-docs/**", "/actuator/*", "/actuator",
                "/actuator/health", "/api/misc/**", "/actuator/health", "/oauth/token", "/swagger-ui-custom.html",
                "/api/misc/**").permitAll()
                .antMatchers(HttpMethod.GET, PERMIT_USER_GET_REQUEST).access("hasRole('COMPANY')")
                .and().exceptionHandling()
                .accessDeniedHandler(new OAuth2AccessDeniedHandler());

    }


}
