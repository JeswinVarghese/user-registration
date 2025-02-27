package com.example.userregistration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.userregistration.model.User;
import com.example.userregistration.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationProvider authenticationProvider)
			throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/users/register", "/api/users/validate")
						.permitAll().requestMatchers("/api/users/all", "/api/users/delete/**").hasAuthority("ADMIN")
						.requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**",
								"/swagger-resources/**", "/webjars/**")
						.permitAll().anyRequest().permitAll())
				.authenticationProvider(authenticationProvider)
				.httpBasic(basic -> {
				});

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepository) {
		return username -> {
			User user = userRepository.findByEmail(username)
					.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

			return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
					.password(user.getPassword()).authorities(user.getAccessType().name()).build();
		};
	}

	@Bean
	public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}
}
