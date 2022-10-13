package system.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import system.entities.Movie;

public interface IMovieRepository  extends JpaRepository<Movie, Integer>{

}
