package telran.ashkelon2018.forum.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
@NoArgsConstructor
public class RolesException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public RolesException(String message) {
		super(message);
	}
}