package nestorcicardini.D9.auth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nestorcicardini.D9.exceptions.InvalidTokenException;
import nestorcicardini.D9.exceptions.NotFoundException;
import nestorcicardini.D9.utenti.Utente;
import nestorcicardini.D9.utenti.UtenteService;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
	@Autowired
	UtenteService utenteService;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Questo metodo prende i header della request e ne estrae la parte che
		// ha come chiave 'Authorization'
		String authHeader = request.getHeader("Authorization");

		// Se questo header non esiste, lancia un'eccezione
		if (authHeader == null || !authHeader.startsWith("Bearer "))
			throw new InvalidTokenException(
					"Attenzione, token mancante sull'authorization header");

		// Prende il valore del token 'sottraendo' le prime 7 lettere (la parola
		// Barer) e ne salva soltanto il token
		String accessToken = authHeader.substring(7);

		// Questo metodo viene utilizzato per verificare la validità del token
		// (Il token NON deve essere modificato e NON deve essere scaduto)
		JWTUtils.tokenValidation(accessToken);

		// Dopo la validazione del token
		// Estraggo l'email dal token e cerco l'utente
		String email = JWTUtils.extractSubject(accessToken);

		// Cerco l'utente in base all'email
		try {
			Utente utenteTrovato = utenteService.findByEmail(email);

			// Il costruttore di questo metodo accetta come primo parametro
			// l'utenre trovato e come terzo parametro viene richiamato il
			// metodo per ottenere il ruolo (in questo caso USER o ADMIN)
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					utenteTrovato, null, utenteTrovato.getAuthorities());
			authToken.setDetails(
					new WebAuthenticationDetailsSource().buildDetails(request));

			// Aggiungo l'utente al SecurityContextHolder ('scatolone' che
			// contiene tutti gli utenti loggati)
			SecurityContextHolder.getContext().setAuthentication(authToken);

			// Puoi procedere al prossimo blocco della filterChain
			filterChain.doFilter(request, response);
		} catch (NotFoundException e) {
			e.printStackTrace();
		}

		// Se non OK -> 401 ("Per favore effettua di nuovo il login")
	}

	// Qui si possono definire quali sono gli endpoin dove questo filtro verra
	// disabilitato
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return new AntPathMatcher().match("/auth/**", request.getServletPath());
	}

}
