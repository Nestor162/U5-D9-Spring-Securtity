package nestorcicardini.D9.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nestorcicardini.D9.auth.payloads.AuthUtentePayload;
import nestorcicardini.D9.auth.payloads.TokenPayload;
import nestorcicardini.D9.exceptions.InvalidCredentialsException;
import nestorcicardini.D9.utenti.Utente;
import nestorcicardini.D9.utenti.UtenteService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	UtenteService utenteService;

	@PostMapping("/register")
	public ResponseEntity<Utente> userRegister(
			@RequestBody @Validated AuthUtentePayload payload) {

		Utente utenteCreato = utenteService.create(payload);
		return new ResponseEntity<>(utenteCreato, HttpStatus.CREATED);

	}

	@PostMapping("/login")
	public ResponseEntity<TokenPayload> userLogin(
			@RequestBody AuthUtentePayload payload) {

		// Verificare credenziali ottenute da payload:
		// 1. Email, controlliamo che l'email esista su db
		Utente utenteTrovato = utenteService.findByEmail(payload.getEmail());

		// 2. Password, controlliamo che la password corrisponda con quella
		// salvata su db
		if (!payload.getPassword().equals(utenteTrovato.getPassword()))
			throw new InvalidCredentialsException("Credenziali errate");

		// Se tutto OK. Viene creato un JWT token (utilizzando classe JWTUtils)
		String token = JWTUtils.createToken(utenteTrovato);

		return new ResponseEntity<TokenPayload>(new TokenPayload(token),
				HttpStatus.OK);

	}
}
