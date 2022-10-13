package system.services;

import java.util.ArrayList;
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

import system.repositories.interfaces.ICharacterRepository;
import system.repositories.interfaces.IMovieRepository;
import system.services.interfaces.ICharacterService;
import system.dto.cruddto.creationdto.CharacterCreationDTO;
import system.dto.cruddto.responsedto.CharacterResponseDTO;
import system.dto.cruddto.responsedto.GenreResponseDTO;
import system.dto.cruddto.responsedto.GetAllResponseDTO;
import system.dto.cruddto.responsedto.MovieResponseDTO;
import system.entities.Character;
import system.entities.Movie;
import system.exceptions.AffectedEntityException;
import system.exceptions.ResourceNotFoundException;

@Service
public class CharacterService implements ICharacterService {

	@Autowired
	private ICharacterRepository characterRepository;

	@Autowired
	private IMovieRepository movieRepository;

	@Override
	public GetAllResponseDTO getAllCharacters(int pageNumber, int elementsByPage, String orderBy, String sortDir,
			String name, Integer age, Integer idMovie) {

		if(name==null && age==null &&idMovie ==null) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(orderBy).descending()
				: Sort.by(orderBy).ascending();
		Pageable pageable = PageRequest.of(pageNumber, elementsByPage, sort);
		Page<Character> characters = characterRepository.findAll(pageable);

		List<CharacterResponseDTO> content = characters.stream().map(character -> mapResponseDTO(character))
				.collect(Collectors.toList());
		return new GetAllResponseDTO(content, pageNumber, elementsByPage, characters.getTotalElements(),
				characters.getTotalPages(), characters.isLast());
		} else {
			List<CharacterResponseDTO> content = characterRepository.findAll().stream().map(character -> mapResponseDTO(character))
					.collect(Collectors.toList());
		if (name != null) {
			content = content.stream().filter(character -> character.getName().contains(name))
					.collect(Collectors.toList());
		}

		if (age != null) {
			content = content.stream().filter(character -> character.getAge() == age).collect(Collectors.toList());
		}

		if (idMovie != null) {
			List<CharacterResponseDTO> content2 = new ArrayList<CharacterResponseDTO>();
			for (CharacterResponseDTO character : content) {
				for(MovieResponseDTO movie : character.getMovies()) {
					if(movie.getId()==idMovie) {
						content2.add(character);
					}
				}
			}
			content=content2;
		}
		return new GetAllResponseDTO(content, 0, content.size(), content.size(), 1, true);
		}
	}

	@Override
	public CharacterResponseDTO getCharacterById(int id) {

		Character character = characterRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Personaje", "id", id));

		return mapResponseDTO(character);
	}

	@Override
	public CharacterResponseDTO createCharacter(CharacterCreationDTO characterCreationDTO) {

		Character character = characterRepository.save(mapEntity(characterCreationDTO));
		return mapResponseDTO(character);

	}

	@Override
	public CharacterResponseDTO updateCharacter(CharacterCreationDTO characterCreationDTO, int id) {

		Character character = characterRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Personaje", "id", id));

		Set<Movie> movies = new HashSet<>();

		for (int movie_id : characterCreationDTO.getMovies_id()) {
			Movie movie = movieRepository.findById(movie_id)
					.orElseThrow(() -> new ResourceNotFoundException("Pelicula", "id", movie_id));
			movies.add(movie);

		}

		character.setName(characterCreationDTO.getName());
		character.setAge(characterCreationDTO.getAge());
		character.setWeight(characterCreationDTO.getWeight());
		character.setHistory(characterCreationDTO.getHistory());
		character.setImageUrl(characterCreationDTO.getImageUrl());
		character.setMovies(movies);

		return mapResponseDTO(characterRepository.save(character));
	}

	@Override
	public void deleteCharacter(int id) {

		Character character = characterRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Personaje", "id", id));

		Set<Movie> movies = character.getMovies();

		for (Movie movie : movies) {
			throw new AffectedEntityException("al personaje", character.getName(), "la pelicula", movie.getTitle());
		}

		characterRepository.delete(character);
	}

	private Character mapEntity(CharacterCreationDTO characterCreationDTO) {

		Set<Movie> movies = new HashSet<>();

		for (int movie_id : characterCreationDTO.getMovies_id()) {
			Movie movie = movieRepository.findById(movie_id)
					.orElseThrow(() -> new ResourceNotFoundException("Pelicula", "id", movie_id));
			movies.add(movie);

		}

		Character character = new Character();

		character.setName(characterCreationDTO.getName());
		character.setAge(characterCreationDTO.getAge());
		character.setWeight(characterCreationDTO.getWeight());
		character.setHistory(characterCreationDTO.getHistory());
		character.setImageUrl(characterCreationDTO.getImageUrl());
		character.setMovies(movies);

		return character;
	}

	private CharacterResponseDTO mapResponseDTO(Character character) {

		CharacterResponseDTO response = new CharacterResponseDTO();
		Set<MovieResponseDTO> moviesResponse = new HashSet<>();

		for (Movie movie : character.getMovies()) {

			GenreResponseDTO genre = new GenreResponseDTO();
			genre.setId(movie.getGenre().getId());
			genre.setName(movie.getGenre().getName());
			genre.setImageUrl(movie.getGenre().getImageUrl());

			MovieResponseDTO movieResponseDTO = new MovieResponseDTO();
			movieResponseDTO.setId(movie.getId());
			movieResponseDTO.setTitle(movie.getTitle());
			movieResponseDTO.setRating(movie.getRating());
			movieResponseDTO.setGenre(genre);
			movieResponseDTO.setImageUrl(movie.getImageUrl());
			moviesResponse.add(movieResponseDTO);
		}

		response.setId(character.getId());
		response.setName(character.getName());
		response.setAge(character.getAge());
		response.setWeight(character.getWeight());
		response.setHistory(character.getHistory());
		response.setImageUrl(character.getImageUrl());
		response.setMovies(moviesResponse);

		return response;
	}

}
