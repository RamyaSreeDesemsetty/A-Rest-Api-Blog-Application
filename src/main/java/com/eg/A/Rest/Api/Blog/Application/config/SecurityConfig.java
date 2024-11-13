package com.eg.A.Rest.Api.Blog.Application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.eg.A.Rest.Api.Blog.Application.security.JwtAuthenticationEntryPoint;
import com.eg.A.Rest.Api.Blog.Application.security.JwtAuthenticationFilter;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@EnableMethodSecurity
@SecurityScheme(name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class SecurityConfig {

	private UserDetailsService userDetailsService;

	private JwtAuthenticationEntryPoint authenticationEntryPoint;

	private JwtAuthenticationFilter authenticationFilter;

	public SecurityConfig(UserDetailsService userDetailsService, JwtAuthenticationEntryPoint authenticationEntryPoint,
			JwtAuthenticationFilter authenticationFilter) {
		super();
		this.userDetailsService = userDetailsService;
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.authenticationFilter = authenticationFilter;
	}

	@Bean
	public AuthenticationManager authenticateManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	/*
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf((csrf) -> csrf.disable()).authorizeHttpRequests((authorize) -> 
                           authorize.anyRequest().authenticated())
		
				.httpBasic(Customizer.withDefaults());
		return http.build();
	}
	*/
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf((csrf) -> csrf.disable()).authorizeHttpRequests((authorize) -> 
		authorize.requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
				.requestMatchers(HttpMethod.POST, "/api/v1/categories/**").permitAll()
				.requestMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()
									.requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
									.requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
                           .anyRequest().authenticated())
                           
		
				.httpBasic(Customizer.withDefaults())
				.exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

							http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
	// not used this code when we provide authentication manger method in config
	/*
	 * @Bean public UserDetailsService userDetailsService() { UserDetails ramya =
	 * User.builder().username("Ramya").password(passwordEncoder().encode("Ramya@12"
	 * )) .roles("USER").build(); UserDetails admin =
	 * User.builder().username("Admin").password(passwordEncoder().encode("Admin@21"
	 * )) .roles("ADMIN").build();
	 * 
	 * return new InMemoryUserDetailsManager(ramya, admin); }
	 */

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
