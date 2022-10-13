package system.dto.cruddto;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public abstract class MovieBaseDTO {

	@NotNull
	@NotEmpty
	@Size(min=2,max=30,message = "El Título de la pelicula debe tener entre 2 y 30 caracteres")
	private String title;
	
	@NotNull
	private Date premiere;
	
	@Min(1)
	@Max(5)
	private int rating;
	
	@NotNull
	@NotEmpty
	@Size(min=2,max=10,message = "La imagen de la pelicula debe tener entre 2 y 10 caracteres")
	private String imageUrl;
	
	public MovieBaseDTO() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getPremiere() {
		return premiere;
	}

	public void setPremiere(Date premiere) {
		this.premiere = premiere;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
