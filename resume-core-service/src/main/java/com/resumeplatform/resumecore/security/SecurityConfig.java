package com.resumeplatform.resumecore.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/*
 * @Configuration
 * 
 * @EnableMethodSecurity public class SecurityConfig {
 * 
 * private final JwtAuthenticationFilter jwtFilter; private final
 * CustomUserDetailsService userDetailsService; private final PasswordEncoder
 * passwordEncoder;
 * 
 * public SecurityConfig(JwtAuthenticationFilter jwtFilter,
 * CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder)
 * { this.jwtFilter = jwtFilter; this.userDetailsService = userDetailsService;
 * this.passwordEncoder = passwordEncoder; }
 * 
 * @Bean public DaoAuthenticationProvider authenticationProvider() {
 * DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
 * provider.setUserDetailsService(userDetailsService);
 * provider.setPasswordEncoder(passwordEncoder); return provider; }
 * 
 * @Bean public SecurityFilterChain filterChain(HttpSecurity http) throws
 * Exception {
 * 
 * http.csrf(csrf ->
 * csrf.disable()).authenticationProvider(authenticationProvider())
 * .authorizeHttpRequests(auth -> auth
 * 
 * .requestMatchers("/auth/**").permitAll()
 * 
 * // Everything else .anyRequest().authenticated()) .addFilterBefore(jwtFilter,
 * UsernamePasswordAuthenticationFilter.class);
 * 
 * return http.build(); }
 * 
 * @Bean public AuthenticationManager authenticationManager(
 * AuthenticationConfiguration config) throws Exception { return
 * config.getAuthenticationManager(); } }
 */




@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter,
                          CustomUserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // 🔥 REQUIRED FOR REACT
            .csrf(csrf -> csrf.disable())

            // 🔥 REQUIRED FOR REACT
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // 🔥 JWT = STATELESS
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authenticationProvider(authenticationProvider())

            .authorizeHttpRequests(auth -> auth
                // 🔓 LOGIN API
                .requestMatchers("/auth/**","/users/**").permitAll()

                // 🔓 PREFLIGHT REQUEST
                .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()

                // 🔐 EVERYTHING ELSE
                .anyRequest().authenticated()
            )

            // 🔥 JWT FILTER
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // 🔥 CORS CONFIG FOR REACT
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of(
        	    "http://localhost:3000",
        	    "https://resume-frontend-taupe.vercel.app"
        	));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(false); // JWT only

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
















