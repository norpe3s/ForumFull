package telran.ashkelon2018.forum.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@ResponseStatus(HttpStatus.NOT_FOUND)
@NoArgsConstructor
public class MetodNotFoundExceptions extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public MetodNotFoundExceptions(String message) {
		super(message);
	}
}