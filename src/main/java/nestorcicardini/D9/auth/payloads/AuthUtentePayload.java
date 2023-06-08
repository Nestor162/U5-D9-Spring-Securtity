package nestorcicardini.D9.auth.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AuthUtentePayload {
	@NotNull(message = "L'attributo username è obbligatorio")
	@Size(min = 3, max = 30)
	private String username;

	@NotNull(message = "L'attributo password è obbligatorio")
	@Size(min = 3, max = 30)
	private String password;

	@NotNull
	@Email(message = "Non hai inserito un email valido")
	private String email;
}
