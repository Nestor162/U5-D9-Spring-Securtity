package nestorcicardini.D9.utenti;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

	@Autowired
	UtenteService utenteService;

	@GetMapping("")
	public List<Utente> getUtenti() {
		return utenteService.findAll();
	}

	@GetMapping("{id}")
	public Optional<Utente> getUtentiById(@PathVariable UUID id) {
		return utenteService.findById(id);
	}

}
