package system.services.interfaces;

import system.dto.cruddto.creationdto.MovieCreationDTO;
import system.dto.cruddto.responsedto.GetAllResponseDTO;
import system.dto.cruddto.responsedto.MovieResponseDTO;

public interface IMovieService {

	public GetAllResponseDTO getAllMovies(int pageNumber, int elementsByPage, String orderBy, String sortDir, String title, Integer idGenre);
	public MovieResponseDTO getMovieById(int id);
	public MovieResponseDTO createMovie(MovieCreationDTO movieCreationDTO);
	public MovieResponseDTO updateMovie(MovieCreationDTO movieCreationDTO, int id);
	public void deleteMovie(int id);
	
	public MovieResponseDTO addCharacter(int idMovie, int idCharacter);
	public MovieResponseDTO removeCharacter(int idMovie, int idCharacter);
}