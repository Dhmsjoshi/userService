package dev.dharam.userservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(request -> {
                    try {
                        request
                                        .anyRequest().permitAll()
                                        .and().csrf().disable();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                );

        http.httpBasic(Customizer.withDefaults());
        http.formLogin(Customizer.withDefaults());
        return http.build();
    }
}
