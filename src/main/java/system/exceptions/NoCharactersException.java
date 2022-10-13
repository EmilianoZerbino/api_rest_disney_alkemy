package system.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NoCharactersException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private String movieName;
	
	public NoCharactersException(String movieName) {
		super(String.format("La pelicula %s debe tener al menos un protagonista, sino este no existe, primero debe crearlo",movieName));
		this.movieName = movieName;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

}
