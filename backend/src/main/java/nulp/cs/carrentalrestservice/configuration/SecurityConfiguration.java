package nulp.cs.carrentalrestservice.configuration;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.controller.*;
import nulp.cs.carrentalrestservice.filter.JwtAuthenticationFilter;
import nulp.cs.carrentalrestservice.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( registry -> {
//                    registry.requestMatchers(AdminController.BASE_PATH, AdminController.BASE_PATH+"/**").hasRole("ADMIN");
                    registry.requestMatchers(HttpMethod.POST, AdminController.BASE_PATH).permitAll();
                    registry.requestMatchers(HttpMethod.POST, AdminController.BASE_PATH+"/authenticate").permitAll();
                    registry.requestMatchers(CarOrderController.BASE_PATH, CarOrderController.BASE_PATH+"/**").hasAnyAuthority("ADMIN", "USER");
                    registry.requestMatchers(CarController.BASE_PATH, CarController.BASE_PATH+"/**").permitAll();
                    registry.requestMatchers(CarPricingController.BASE_PATH, CarPricingController.BASE_PATH+"/**").hasAnyAuthority("ADMIN");
                    registry.requestMatchers(MaintenanceController.BASE_PATH, MaintenanceController.BASE_PATH+"/**").hasRole("ADMIN");
                    registry.requestMatchers(MaintenanceController.BASE_PATH, MaintenanceController.BASE_PATH+"/**").permitAll();
                    registry.anyRequest().authenticated();
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
