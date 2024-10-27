package dev.edm115.apigateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    /* @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        http
            .authorizeExchange()
            .pathMatchers("/login", "/public/**").permitAll()
            .anyExchange().authenticated()
            .and()
            .httpBasic()
            .and()
            .csrf().disable();

        return http.build();
    } */

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
        http
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers("/ms-user/**", "/ms-location/**").authenticated()
                .anyExchange().permitAll()
            )
            .csrf().disable()
            .headers().frameOptions().disable()
            .and()
            .httpBasic();
        
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("azertyuiop")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
