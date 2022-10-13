package system.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import system.exceptions.InvalidAlterCharacterException;
import system.exceptions.NoCharactersException;
import system.exceptions.ResourceNotFoundException;
import system.dto.cruddto.creationdto.MovieCreationDTO;
import system.dto.cruddto.responsedto.CharacterResponseDTO;
import system.dto.cruddto.responsedto.GenreResponseDTO;
import system.dto.cruddto.responsedto.GetAllResponseDTO;
import system.dto.cruddto.responsedto.MovieResponseDTO;
import system.entities.Genre;
import system.entities.Movie;
import system.entities.Character;
import system.repositories.interfaces.ICharacterRepository;
import system.repositories.interfaces.IGenreRepository;
import system.repositories.interfaces.IMovieRepository;
import system.services.interfaces.IMovieService;

@Service
public class MovieService implements IMovieService {

	@Autowired
	private IMovieRepository movieRepository;

	@Autowired
	private IGenreRepository genreRepository;

	@Autowired
	private ICharacterRepository characterRepository;

	@Override
	public GetAllResponseDTO getAllMovies(int pageNumber, int elementsByPage, String orderBy, String sortDir,
			String title, Integer idGenre) {
		if (title == null && idGenre == null) {
			Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(orderBy).descending()
					: Sort.by(orderBy).ascending();
			Pageable pageable = PageRequest.of(pageNumber, elementsByPage, sort);
			Page<Movie> movies = movieRepository.findAll(pageable);
			List<MovieResponseDTO> content = movies.stream().map(movie -> mapResponseDTO(movie))
					.collect(Collectors.toList());
			return new GetAllResponseDTO(content, pageNumber, elementsByPage, movies.getTotalElements(),
					movies.getTotalPages(), movies.isLast());
		} else {
			List<MovieResponseDTO> content = movieRepository.findAll().stream().map(movie -> mapResponseDTO(movie))
					.collect(Collectors.toList());

			if (title != null) {
				content = content.stream().filter(movie -> movie.getTitle().contains(title))
						.collect(Collectors.toList());
			}

			if (idGenre != null) {
				content = content.stream().filter(movie -> movie.getGenre().getId() == idGenre)
						.collect(Collectors.toList());
			}
			return new GetAllResponseDTO(content, 0, content.size(), content.size(), 1, true);
		}

	}

	@Override
	public MovieResponseDTO getMovieById(int id) {

		Movie movie = movieRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pelicula", "id", id));

		return mapResponseDTO(movie);
	}

	@Override
	public MovieResponseDTO createMovie(MovieCreationDTO movieCreationDTO) {

		Movie movie = movieRepository.save(mapEntity(movieCreationDTO));
		return mapResponseDTO(movie);

	}

	@Override
	public MovieResponseDTO updateMovie(MovieCreationDTO movieCreationDTO, int id) {

		Movie movie = movieRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pelicula", "id", id));

		Genre genre = genreRepository.findById(movieCreationDTO.getGenre_id())
				.orElseThrow(() -> new ResourceNotFoundException("Género", "id", movieCreationDTO.getGenre_id()));

		if (movieCreationDTO.getCharacters_id().length == 0) {
			throw new NoCharactersException(movieCreationDTO.getTitle());
		}

		movie.setTitle(movieCreationDTO.getTitle());
		movie.setPremiere(movieCreationDTO.getPremiere());
		movie.setRating(movieCreationDTO.getRating());
		movie.setImageUrl(movieCreationDTO.getImageUrl());
		movie.setGenre(genre);

		Set<Character> characters = new HashSet<>();
		for (int character_id : movieCreationDTO.getCharacters_id()) {
			Character character = characterRepository.findById(character_id)
					.orElseThrow(() -> new ResourceNotFoundException("Personaje", "id", character_id));

			characters.add(character);
		}

		movie.setCharacters(characters);

		return mapResponseDTO(movieRepository.save(movie));
	}

	@Override
	public void deleteMovie(int id) {

		Movie movie = movieRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pelicula", "id", id));

		movieRepository.delete(movie);
	}
	
	@Override
	public MovieResponseDTO addCharacter(int idMovie, int idCharacter) {
		
		Movie movie = movieRepository.findById(idMovie)
				.orElseThrow(() -> new ResourceNotFoundException("Pelicula", "id", idMovie));
		
		Character character = characterRepository.findById(idCharacter)
				.orElseThrow(() -> new ResourceNotFoundException("Personaje", "id", idCharacter));
		
		Set<Character> characters = movie.getCharacters();
		characters.add(character);
		
		movie.setCharacters(characters);
		
		return mapResponseDTO(movieRepository.save(movie));
	}
	
	@Override
	public MovieResponseDTO removeCharacter(int idMovie, int idCharacter) {
		
		Movie movie = movieRepository.findById(idMovie)
				.orElseThrow(() -> new ResourceNotFoundException("Pelicula", "id", idMovie));
		
		Character character = characterRepository.findById(idCharacter)
				.orElseThrow(() -> new ResourceNotFoundException("Personaje", "id", idCharacter));
		
		Set<Character> characters = movie.getCharacters();
		
		if(!characters.contains(character)){
			throw new InvalidAlterCharacterException(0,idMovie, idCharacter);
		}
		
		if(characters.size()==1) {
			throw new InvalidAlterCharacterException(1,idMovie, idCharacter);
		}
		
		characters.remove(character);
		
		movie.setCharacters(characters);
		
		return mapResponseDTO(movieRepository.save(movie));
	}

	private Movie mapEntity(MovieCreationDTO movieCreationDTO) {

		Genre genre = genreRepository.findById(movieCreationDTO.getGenre_id())
				.orElseThrow(() -> new ResourceNotFoundException("Género", "id", movieCreationDTO.getGenre_id()));

		if (movieCreationDTO.getCharacters_id().length == 0) {
			throw new NoCharactersException(movieCreationDTO.getTitle());
		}

		Movie movie = new Movie();
		movie.setTitle(movieCreationDTO.getTitle());
		movie.setPremiere(movieCreationDTO.getPremiere());
		movie.setRating(movieCreationDTO.getRating());
		movie.setImageUrl(movieCreationDTO.getImageUrl());
		movie.setGenre(genre);

		Set<Character> characters = new HashSet<>();
		for (int character_id : movieCreationDTO.getCharacters_id()) {
			Character character = characterRepository.findById(character_id)
					.orElseThrow(() -> new ResourceNotFoundException("Personaje", "id", character_id));

			characters.add(character);
		}

		movie.setCharacters(characters);

		return movie;

	}

	private MovieResponseDTO mapResponseDTO(Movie movie) {

		MovieResponseDTO response = new MovieResponseDTO();

		Set<CharacterResponseDTO> charactersDTO = new HashSet<>();
		for (Character character : movie.getCharacters()) {

			CharacterResponseDTO CharacterResponseDTO = new CharacterResponseDTO();
			CharacterResponseDTO.setId(character.getId());
			CharacterResponseDTO.setName(character.getName());
			CharacterResponseDTO.setAge(character.getAge());
			CharacterResponseDTO.setWeight(character.getWeight());
			CharacterResponseDTO.setHistory(character.getHistory());
			CharacterResponseDTO.setImageUrl(character.getImageUrl());
			charactersDTO.add(CharacterResponseDTO);
		}

		GenreResponseDTO genre = new GenreResponseDTO();
		genre.setId(movie.getGenre().getId());
		genre.setName(movie.getGenre().getName());
		genre.setImageUrl(movie.getGenre().getImageUrl());

		response.setId(movie.getId());
		response.setTitle(movie.getTitle());
		response.setPremiere(movie.getPremiere());
		response.setRating(movie.getRating());
		response.setImageUrl(movie.getImageUrl());
		response.setGenre(genre);
		response.setCharacters(charactersDTO);

		return response;
	}

}
