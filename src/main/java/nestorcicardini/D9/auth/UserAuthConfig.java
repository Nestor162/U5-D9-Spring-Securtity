package nestorcicardini.D9.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

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
				auth -> auth.requestMatchers("/auth/**").permitAll());
		http.authorizeHttpRequests(
				auth -> auth.requestMatchers("/utenti/**").permitAll()); // cambiare
																			// a
																			// authenticated

//		http.addFilterBefore(jwtAuthFilter,
//				UsernamePasswordAuthenticationFilter.class);

		http.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}

}
