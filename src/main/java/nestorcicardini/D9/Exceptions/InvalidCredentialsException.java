package nestorcicardini.D9.exceptions;

@SuppressWarnings("serial")
public class InvalidCredentialsException extends RuntimeException {

	public InvalidCredentialsException(String message) {
		super(message);
	}
}
