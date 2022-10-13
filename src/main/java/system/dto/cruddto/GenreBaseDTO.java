package system.dto.cruddto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public abstract class GenreBaseDTO {

	@NotNull
	@NotEmpty
	@Size(min=2,max=15,message = "El Nombre del género debe tener entre 2 y 15 caracteres")
	private String name;
	
	@NotNull
	@NotEmpty
	@Size(min=2,max=10,message = "La imagen del género debe tener entre 2 y 10 caracteres")
	private String imageUrl;
	

	public GenreBaseDTO() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}
