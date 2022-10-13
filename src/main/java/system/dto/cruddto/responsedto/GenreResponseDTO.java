package system.dto.cruddto.responsedto;

import system.dto.cruddto.GenreBaseDTO;

public class GenreResponseDTO extends GenreBaseDTO{

	private int id;
	
	public GenreResponseDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
