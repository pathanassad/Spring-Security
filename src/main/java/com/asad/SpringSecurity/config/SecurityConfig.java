package com.asad.SpringSecurity.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

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

     @Bean
     public UserDetailsService userDetailsService(){
         UserDetails user1 = User
                 .withDefaultPasswordEncoder()
                 .username("Altamash")
                 .password("Pass@123")
                 .roles("USER")
                 .build();

         UserDetails user2 = User
                 .withDefaultPasswordEncoder()
                 .username("Abdul Karim")
                 .password("Pass@123")
                 .roles("Admin")
                 .build();

         return new InMemoryUserDetailsManager(user1, user2);
     }

     @Bean
    public AuthenticationProvider authenticationProvider(){
         DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
         provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
         provider.setUserDetailsService(userDetailsService);
         return provider;
     }

}
