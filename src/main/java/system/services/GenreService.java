package system.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import system.dto.cruddto.creationdto.GenreCreationDTO;
import system.dto.cruddto.responsedto.GenreResponseDTO;
import system.dto.cruddto.responsedto.GetAllResponseDTO;
import system.entities.Genre;
import system.entities.Movie;
import system.exceptions.AffectedEntityException;
import system.exceptions.ResourceNotFoundException;
import system.repositories.interfaces.IGenreRepository;
import system.repositories.interfaces.IMovieRepository;
import system.services.interfaces.IGenreService;

@Service
public class GenreService implements IGenreService {

	@Autowired
	private IGenreRepository genreRepository;
	
	@Autowired
	private IMovieRepository movieRepository;

	@Override
	public GetAllResponseDTO getAllGenres(int pageNumber, int elementsByPage, String orderBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(orderBy).descending()
				: Sort.by(orderBy).ascending();
		Pageable pageable = PageRequest.of(pageNumber, elementsByPage, sort);
		Page<Genre> genres = genreRepository.findAll(pageable);
		List<GenreResponseDTO> content= genres.stream().map(genre -> mapResponseDTO(genre)).collect(Collectors.toList());
		
		return new GetAllResponseDTO(content,pageNumber,elementsByPage,genres.getTotalElements(),
                genres.getTotalPages(),genres.isLast());

	}

	@Override
	public GenreResponseDTO getGenreById(int id) {

		Genre genre = genreRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Género", "id", id));

		return mapResponseDTO(genre);
	}

	@Override
	public GenreResponseDTO createGenre(GenreCreationDTO genreCreationDTO) {

		Genre genre = genreRepository.save(mapGenre(genreCreationDTO));
		return mapResponseDTO(genre);
	}

	@Override
	public GenreResponseDTO updateGenre(GenreCreationDTO genreCreationDTO, int id) {

		Genre genre = genreRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Género", "id", id));

		genre.setName(genreCreationDTO.getName());
		genre.setImageUrl(genreCreationDTO.getImageUrl());

		return mapResponseDTO(genreRepository.save(genre));
	}
	
	@Override
	public void deleteGenre(int id) {
		
		Genre genre = genreRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pelicula","id",id));
		
		List<Movie> movies = movieRepository.findAll();

		for(Movie movie : movies) {
			if(movie.getGenre().getId()==id) {
				throw new AffectedEntityException ("el género",genre.getName(),"la pelicula",movie.getTitle());
			}
		}
		
		genreRepository.delete(genre);
	}

	private Genre mapGenre(GenreCreationDTO genreCreationDTO) {

		Genre genre = new Genre();
		genre.setName(genreCreationDTO.getName());
		genre.setImageUrl(genreCreationDTO.getImageUrl());

		return genre;
	}

	private GenreResponseDTO mapResponseDTO(Genre genre) {

		GenreResponseDTO response = new GenreResponseDTO();

		response.setId(genre.getId());
		response.setName(genre.getName());
		response.setImageUrl(genre.getImageUrl());

		return response;
	}

}
