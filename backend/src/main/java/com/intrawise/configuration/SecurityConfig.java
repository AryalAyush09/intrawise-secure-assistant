package com.intrawise.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.intrawise.JWT.JwtFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {
	
    private final JwtFilter jwtFilter;
	
    @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
		 .csrf(crsf -> crsf.disable())
		 .cors(cors -> cors.configurationSource(corsConfigurationSource()))
		 
		 .authorizeHttpRequests(auth -> auth
				 .requestMatchers(HttpMethod.OPTIONS).permitAll()
		 .requestMatchers("/api/admin/**").hasRole("ADMIN")
		 .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
		 .requestMatchers(
				 "/api/v1/auth/login",
                 "/api/v1/auth/register"
		 ).permitAll()
		 .anyRequest().authenticated()
		 )
          .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
          return http.build();
          
	}

    @Bean
	public CorsConfigurationSource corsConfigurationSource() {
	  CorsConfiguration config = new CorsConfiguration();
	    config.setAllowedOrigins(List.of("http://localhost:5173"));
	    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	    config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
	    config.setAllowCredentials(true);
	    
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        
		return source;
	}
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
