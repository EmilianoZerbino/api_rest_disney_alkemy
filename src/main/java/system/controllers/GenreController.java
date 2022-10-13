package system.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import system.dto.cruddto.creationdto.GenreCreationDTO;
import system.dto.cruddto.responsedto.GenreResponseDTO;
import system.dto.cruddto.responsedto.GetAllResponseDTO;
import system.services.interfaces.IGenreService;
import system.utilities.AppConst;

@RestController
@RequestMapping("/api/genre")
public class GenreController {

	@Autowired
	private IGenreService genreService;

	@GetMapping
	public ResponseEntity<GetAllResponseDTO> GetAll(
			@RequestParam(value = "pageNumber", defaultValue = AppConst.defaultPageNumber, required = false) int pageNumber,
			@RequestParam(value = "elementsByPage", defaultValue = AppConst.defaultElementsByPage, required = false) int elementsByPage,
			@RequestParam(value = "sortBy", defaultValue = AppConst.defaultElementOrderPage, required = false) String orderBy,
			@RequestParam(value = "sortDir", defaultValue = AppConst.defaultDireccionOrderPage, required = false) String sortDir) {

		return ResponseEntity.ok(genreService.getAllGenres(pageNumber, elementsByPage, orderBy, sortDir));
	}

	@GetMapping("/{id}")
	public ResponseEntity<GenreResponseDTO> GetById(@PathVariable(name = "id") int id) {
		return ResponseEntity.ok(genreService.getGenreById(id));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<GenreResponseDTO> Post(@Valid @RequestBody GenreCreationDTO genreCreationDTO) {
		return new ResponseEntity<>(genreService.createGenre(genreCreationDTO), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<GenreResponseDTO> Put(@Valid @RequestBody GenreCreationDTO genreCreationDTO,
			@PathVariable(name = "id") int id) {
		return new ResponseEntity<>(genreService.updateGenre(genreCreationDTO, id), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> Delete(@PathVariable(name = "id") int id) {
		genreService.deleteGenre(id);
		return new ResponseEntity<>("Género eliminado con éxito", HttpStatus.OK);
	}

}
