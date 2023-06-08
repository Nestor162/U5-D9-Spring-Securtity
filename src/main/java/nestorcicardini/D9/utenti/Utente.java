package nestorcicardini.D9.utenti;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "utenti")
public class Utente {
	@Id
	private String email;

	private String username;
	private String password;

	public Utente(String username, String password, String email) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}

}
