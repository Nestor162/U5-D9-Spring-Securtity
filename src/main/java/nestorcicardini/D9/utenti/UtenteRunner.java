package nestorcicardini.D9.utenti;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

@Component
public class UtenteRunner implements CommandLineRunner {

	@Autowired
	UtenteService utenteService;

	@Override
	public void run(String... args) throws Exception {

		Faker faker = new Faker(new Locale("it"));
		for (int i = 0; i < 3; i++) {
			try {

				String nome = faker.name().firstName();
				String cognome = faker.name().lastName();
				String email = faker.internet().emailAddress();
				Utente utente = new Utente(nome, cognome, email);

//				utenteService.createRandom(utente);
//				System.out.println("Salvato utente");

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

}
