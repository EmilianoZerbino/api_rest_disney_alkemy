package system.dto.cruddto.creationdto;

import system.dto.cruddto.CharacterBaseDTO;

public class CharacterCreationDTO extends CharacterBaseDTO{
	
	private int[] movies_id;

	public int[] getMovies_id() {
		return movies_id;
	}

	public void setMovies_id(int[] characters_id) {
		this.movies_id = characters_id;
	}
}
