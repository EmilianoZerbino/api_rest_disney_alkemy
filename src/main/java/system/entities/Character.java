package system.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "characters")
public class Character {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	@Column(nullable = false)
	private int age;
	
	@Column(nullable = false)
	private double weight;
	
	@Column(nullable = false)
	private String history;
	
	@Column(nullable = false)
	private String imageUrl;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "characters_movies",joinColumns = @JoinColumn(name = "character_id",referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name = "movie_id",referencedColumnName = "id"))
	private Set<Movie> movies = new HashSet<>();

	public Character() {
		super();
	}

	public Character(int id, String name, int age, double weight, String history, String imageUrl) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.weight = weight;
		this.history = history;
		this.imageUrl = imageUrl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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
	
	public Set<Movie> getMovies() {
		return movies;
	}

	public void setMovies(Set<Movie> optional) {
		this.movies = optional;
	}
	
}
