package system.dto.cruddto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public abstract class CharacterBaseDTO {

	@NotNull
	@NotEmpty
	@Size(min=2,max=30,message = "El Nombre del personaje debe tener entre 2 y 30 caracteres")
	private String name;
	
	@Min(0)
	@Max(150)
	private int age;
	
	@Min(0)
	@Max(100)
	private double weight;
	
	@NotNull
	@NotEmpty
	@Size(min=2,max=500,message = "El historia del personaje debe tener entre 2 y 500 caracteres")
	private String history;
	
	@NotNull
	@NotEmpty
	@Size(min=2,max=10,message = "La imagen del personaje debe tener entre 2 y 10 caracteres")
	private String imageUrl;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}
