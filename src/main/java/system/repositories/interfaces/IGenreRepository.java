package system.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import system.entities.Genre;

public interface IGenreRepository  extends JpaRepository<Genre, Integer>{

}
