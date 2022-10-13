package system.dto.cruddto.creationdto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import system.dto.cruddto.MovieBaseDTO;

public class MovieCreationDTO extends MovieBaseDTO{

	@NotNull
	private int genre_id;
	
	@NotNull
	@NotEmpty
	private int[] characters_id;

	public int getGenre_id() {
		return genre_id;
	}

	public void setGenre_id(int genre_id) {
		this.genre_id = genre_id;
	}
	
	public int[] getCharacters_id() {
		return characters_id;
	}

	public void setCharacters_id(int[] characters_id) {
		this.characters_id = characters_id;
	}
}
