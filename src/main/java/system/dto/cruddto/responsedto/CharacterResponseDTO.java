package system.dto.cruddto.responsedto;

import java.util.HashSet;
import java.util.Set;

import system.dto.cruddto.CharacterBaseDTO;

public class CharacterResponseDTO  extends CharacterBaseDTO{

	private int id;
	
	private Set<MovieResponseDTO> movies = new HashSet<>();
	
	public CharacterResponseDTO() {
	}
	
	public CharacterResponseDTO(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<MovieResponseDTO> getMovies() {
		return movies;
	}

	public void setMovies(Set<MovieResponseDTO> movies) {
		this.movies = movies;
	}
}
