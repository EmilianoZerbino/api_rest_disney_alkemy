package system.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class InvalidAlterCharacterException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private int idMovie;
	private int idCharacter;
	private static String[] response = {"La pelicula con Id: '%s no posee la actuación del Personaje con Id: '%s",
										"La pelicula con Id: '%s' no puede quedar sin personajes asociados, adhiera a menos un personaje antes de eliminar al personaje con Id: '%s'"};
	
	public InvalidAlterCharacterException(int idResponse, int idMovie, int idCharacter) {
		super(String.format(response[idResponse],idMovie,idCharacter));
		
		this.idMovie=idMovie;
		this.idCharacter=idCharacter;
	}

	public int getIdMovie() {
		return idMovie;
	}

	public void setIdMovie(int idMovie) {
		this.idMovie = idMovie;
	}

	public int getIdCharacter() {
		return idCharacter;
	}

	public void setIdCharacter(int idCharacter) {
		this.idCharacter = idCharacter;
	}

	
}

