package nestorcicardini.D9.auth;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import nestorcicardini.D9.utenti.Utente;

@Component
public class JWTUtils {

	private static String secret;
	private static int expiration;

	@Value("${spring.application.jwt.secret}")
	public void setSecret(String secretKey) {
		secret = secretKey;
	}

	@Value("${spring.application.jwt.expirationindays}")
	public void setExpiration(String expirationInDays) {
		expiration = Integer.parseInt(expirationInDays) * 24 * 60 * 60 * 1000;
	}

	public static String createToken(Utente u) {
		String token = Jwts.builder().setSubject(u.getEmail())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(
						new Date(System.currentTimeMillis() + expiration))
				.signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();

		return token;
	}
}
