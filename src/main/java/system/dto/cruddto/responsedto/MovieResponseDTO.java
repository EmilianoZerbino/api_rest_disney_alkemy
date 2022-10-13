package system.dto.cruddto.responsedto;

import java.util.HashSet;
import java.util.Set;

import system.dto.cruddto.MovieBaseDTO;

public class MovieResponseDTO extends MovieBaseDTO{

	private int id;
	private GenreResponseDTO genre;
	
	private Set<CharacterResponseDTO> characters = new HashSet<>();

	public MovieResponseDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public GenreResponseDTO getGenre() {
		return genre;
	}

	public void setGenre(GenreResponseDTO genre) {
		this.genre = genre;
	}
	
	public Set<CharacterResponseDTO> getCharacters() {
		return characters;
	}

	public void setCharacters(Set<CharacterResponseDTO> characters) {
		this.characters = characters;
	}

}
