package system.services.interfaces;

import system.dto.cruddto.creationdto.GenreCreationDTO;
import system.dto.cruddto.responsedto.GenreResponseDTO;
import system.dto.cruddto.responsedto.GetAllResponseDTO;

public interface IGenreService {

	public GetAllResponseDTO getAllGenres(int pageNumber, int elementsByPage, String orderBy, String sortDir);
	public GenreResponseDTO getGenreById(int id);
	public GenreResponseDTO createGenre(GenreCreationDTO genreCreationDTO);
	public GenreResponseDTO updateGenre(GenreCreationDTO genreDTO, int id);
	public void deleteGenre(int id);
}
