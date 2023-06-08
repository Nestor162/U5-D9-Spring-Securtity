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
import nestorcicardini.D9.utenti.Utente;
import nestorcicardini.D9.utenti.UtenteService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	UtenteService utenteService;

	@PostMapping("/register")
	public ResponseEntity<Utente> userLogin(
			@RequestBody @Validated AuthUtentePayload payload) {

		Utente utenteCreato = utenteService.create(payload);
		return new ResponseEntity<>(utenteCreato, HttpStatus.CREATED);

	}

	@PostMapping("/login")
	public ResponseEntity<AuthUtentePayload> userRegister() {
		return null;

	}
}
