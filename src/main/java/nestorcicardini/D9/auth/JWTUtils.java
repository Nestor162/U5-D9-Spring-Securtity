package nestorcicardini.D9.auth;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import nestorcicardini.D9.exceptions.InvalidTokenException;
import nestorcicardini.D9.utenti.Utente;

@Component
public class JWTUtils {

	private static String secret;
	private static int expiration;

	// Prende il 'segreto' dal file di ambiente (non committato)
	@Value("${spring.application.jwt.secret}")
	public void setSecret(String secretKey) {
		secret = secretKey;
	}

	// Prende il tempo di validita (espresso in giorni) dal file di ambiente
	@Value("${spring.application.jwt.expirationindays}")
	public void setExpiration(String expirationInDays) {
		// Converte il tempo in millisecondi
		expiration = Integer.parseInt(expirationInDays) * 24 * 60 * 60 * 1000;
	}

	// Metodo per creare un token valido. Viene utilizzato un builder che prende
	// 4 valori:
	// 1. La chiave primaria (in questo caso email)
	// 2. Il tempo di emissione in millisecondi
	// 3. Il tempo di scadenza in millisecondi (Data attuale + giorni prima
	// della scadenza)
	// 4. Il 'segreto' che insieme agli altri valori e seguendo un determinato
	// algoritmo ('HS256') elaborano un token autorizzato ( Attenzione! Chiunque
	// abbia il segreto puo elaborare token validi!!)

	public static String createToken(Utente u) {
		String token = Jwts.builder().setSubject(u.getEmail())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(
						new Date(System.currentTimeMillis() + expiration))
				.signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();

		return token;
	}

	// Metodo per validare il token ottenuto da Frontend
	public static void tokenValidation(String token) {
		try {
			// Esegue l'algoritmo ('HS256') per verificare la validità del token
			// Per capire meglio le parti di questo token --> https://jwt.io/
			Jwts.parserBuilder()
					.setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
					.build().parse(token);

			// Si distinguono 2 eccezzioni principali: Token non valido
			// (modificato in qualche maniera) o scaduto
		} catch (MalformedJwtException e) {
			throw new InvalidTokenException("Il token non è valido");
		} catch (ExpiredJwtException e) {
			throw new InvalidTokenException("Il token è scaduto");
		} catch (Exception e) {
			throw new InvalidTokenException(
					"Problemi col token, per favore effettua di nuovo il login");
		}
	}

	// Metodo utilizzato per estrarre il Subjet (in questo caso l'email)
	static public String extractSubject(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build()
				.parseClaimsJws(token).getBody().getSubject();
	}
}
