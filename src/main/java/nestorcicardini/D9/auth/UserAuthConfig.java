package nestorcicardini.D9.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class UserAuthConfig {
	@Autowired
	JWTAuthFilter jwtAuthFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http)
			throws Exception {
		http.cors(c -> c.disable());

		http.csrf(c -> c.disable());

		http.authorizeHttpRequests(
				// Per fare il login/registrazione naturalmente non viene
				// richiesto il token (non ancora generato)
				auth -> auth.requestMatchers("/auth/**").permitAll());
		http.authorizeHttpRequests(
				// Solamente gli utenti autorizzati potranno accedere
				// al'endpoint 'utenti' e derivati
				auth -> auth.requestMatchers("/utenti/**").authenticated());

		http.addFilterBefore(jwtAuthFilter,
				UsernamePasswordAuthenticationFilter.class);

		http.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}

}
