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

import system.dto.cruddto.creationdto.MovieCreationDTO;
import system.dto.cruddto.responsedto.GetAllResponseDTO;
import system.dto.cruddto.responsedto.MovieResponseDTO;
import system.services.interfaces.IMovieService;
import system.utilities.AppConst;


@RestController
@RequestMapping("/api/movies")
public class MovieController {

	@Autowired
	private IMovieService movieService;

	@GetMapping
	public ResponseEntity<GetAllResponseDTO> GetAll(
			@RequestParam(value = "pageNumber", defaultValue = AppConst.defaultPageNumber, required = false) int pageNumber,
			@RequestParam(value = "elementsByPage", defaultValue = AppConst.defaultElementsByPage, required = false) int elementsByPage,
			@RequestParam(value = "sortBy", defaultValue = AppConst.defaultElementOrderPage, required = false) String orderBy,
			@RequestParam(value = "sortDir", defaultValue = AppConst.defaultDireccionOrderPage, required = false) String sortDir,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "idGenre", required = false) Integer idGenre) {

		return ResponseEntity.ok(movieService.getAllMovies(pageNumber, elementsByPage, orderBy, sortDir, title, idGenre));
	}

	@GetMapping("/{id}")
	public ResponseEntity<MovieResponseDTO> GetById(@PathVariable(name = "id") int id) {
		return ResponseEntity.ok(movieService.getMovieById(id));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<?> Post(@Valid @RequestBody MovieCreationDTO movieCreationDTO) {
		return new ResponseEntity<>(movieService.createMovie(movieCreationDTO), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<MovieResponseDTO> Put(
			@Valid @RequestBody MovieCreationDTO movieCreationDTO,
			@PathVariable(name = "id") int id) {
		return new ResponseEntity<>(movieService.updateMovie(movieCreationDTO, id), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> Delete(@PathVariable(name = "id") int id) {
		movieService.deleteMovie(id);
		return new ResponseEntity<>("Pelicula eliminada con éxito", HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/{idMovie}/characters/{idCharacter}")
	public ResponseEntity<MovieResponseDTO> PutCharacter(
			@PathVariable(name = "idMovie") int idMovie,
			@PathVariable(name = "idCharacter") int idCharacter){
		
		return new ResponseEntity<MovieResponseDTO>(movieService.addCharacter(idMovie, idCharacter), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{idMovie}/characters/{idCharacter}")
	public ResponseEntity<MovieResponseDTO> DeleteCharacter(
			@PathVariable(name = "idMovie") int idMovie,
			@PathVariable(name = "idCharacter") int idCharacter){
		
		return new ResponseEntity<MovieResponseDTO>(movieService.removeCharacter(idMovie, idCharacter), HttpStatus.OK);
	}
}
