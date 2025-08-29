package com.instaapproval.api.config; 
 
import org.springframework.context.annotation.*; 
import org.springframework.http.HttpMethod; 
import org.springframework.security.authentication.*; 
import org.springframework.security.config.Customizer; 
import org.springframework.security.config.annotation.web.builders.HttpSecurity; 
import org.springframework.security.config.http.SessionCreationPolicy; 
import org.springframework.security.crypto.bcrypt.*; 
import org.springframework.security.web.*; 
import 
org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; 
 
@Configuration 
public class SecurityConfig { 
    private final JwtAuthFilter jwtAuthFilter; 
    public SecurityConfig(JwtAuthFilter jwtAuthFilter) { 
        this.jwtAuthFilter = jwtAuthFilter; 
    } 
 
    @Bean 
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { 
        http.csrf(csrf -> csrf.disable()) 
           .sessionManagement(sm -> 
sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
           .authorizeHttpRequests(auth -> auth 
               .requestMatchers( 
                   "/api/v1/auth/**", 
                   "/api/v1/swagger/**", 
                   "/api/v1/api-docs/**" 
               ).permitAll() 
               .requestMatchers(HttpMethod.GET, 
"/api/v1/loans/status/**").hasAnyRole("CUSTOMER","ADMIN") 
               .requestMatchers("/api/v1/admin/**").hasRole("ADMIN") 
               .requestMatchers("/api/v1/**").hasAnyRole("CUSTOMER","ADMIN") 
               .anyRequest().authenticated() 
           ) 
           .httpBasic(Customizer.withDefaults()); 
        http.addFilterBefore(jwtAuthFilter, 
        UsernamePasswordAuthenticationFilter.class); 
        return http.build(); 
    } 
 
    @Bean 
    public AuthenticationManager authenticationManager() { 
        return authentication -> { 
            // We authenticate manually in AuthController; return as-is. 
            return authentication; 
        }; 
    } 
 
    @Bean 
    public BCryptPasswordEncoder passwordEncoder() { 
        return new BCryptPasswordEncoder(); 
    } 
}