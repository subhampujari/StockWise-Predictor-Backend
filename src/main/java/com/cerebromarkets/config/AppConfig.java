package com.cerebromarkets.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class AppConfig {

    @Bean
   public  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.sessionManagement(mangagment->mangagment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(Authorizae->Authorizae.requestMatchers("/api/**").authenticated().anyRequest().permitAll())
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class).csrf(csrf->csrf.disable())
                .cors(cors->cors.configurationSource(corsConfigurationSource()));
        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
       return null;
    }
}
