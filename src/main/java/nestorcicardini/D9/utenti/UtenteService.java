package nestorcicardini.D9.utenti;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nestorcicardini.D9.auth.payloads.AuthUtentePayload;
import nestorcicardini.D9.exceptions.BadRequestException;
import nestorcicardini.D9.exceptions.NotFoundException;

@Service
public class UtenteService {

	@Autowired
	private UtenteRepository utenteRepo;

	public Utente create(AuthUtentePayload u) {

		// Controlla se esiste gia un utente con l'email indicata
		utenteRepo.findByEmail(u.getEmail()).ifPresent(utente -> {
			throw new BadRequestException(
					"L'indirizzo email" + utente.getEmail() + " è già in uso");
		});

		Utente nuovoUtente = new Utente(u.getUsername(), u.getPassword(),
				u.getEmail());
		return utenteRepo.save(nuovoUtente);
	}

	public Utente createRandom(Utente u) {
		return utenteRepo.save(u);
	}

	public List<Utente> findAll() {
		return utenteRepo.findAll();
	}

	public Utente findByEmail(String email) throws NotFoundException {
		return utenteRepo.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("Email non trovata"));
	}
}
