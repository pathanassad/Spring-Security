package com.asad.SpringSecurity.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

     @Bean
     public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception{
         http.csrf(customizer -> customizer.disable());
         http.authorizeHttpRequests(request -> request.anyRequest().authenticated());
//         http.formLogin(Customizer.withDefaults());
         http.httpBasic(Customizer.withDefaults());
         http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
         return http.build();

         // you can also use builder pattern here
         
     }
}
