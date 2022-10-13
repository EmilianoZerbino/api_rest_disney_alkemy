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

import system.dto.cruddto.creationdto.CharacterCreationDTO;
import system.dto.cruddto.responsedto.CharacterResponseDTO;
import system.dto.cruddto.responsedto.GetAllResponseDTO;
import system.services.interfaces.ICharacterService;
import system.utilities.AppConst;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {

	@Autowired
	private ICharacterService characterService;

	@GetMapping
	public ResponseEntity<GetAllResponseDTO> GetAll(
			@RequestParam(value = "pageNumber", defaultValue = AppConst.defaultPageNumber, required = false) int pageNumber,
			@RequestParam(value = "elementsByPage", defaultValue = AppConst.defaultElementsByPage, required = false) int elementsByPage,
			@RequestParam(value = "sortBy", defaultValue = AppConst.defaultElementOrderPage, required = false) String orderBy,
			@RequestParam(value = "sortDir", defaultValue = AppConst.defaultDireccionOrderPage, required = false) String sortDir,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "age", required = false) Integer edad,
			@RequestParam(value = "idMovie", required = false) Integer idMovie) {

		return ResponseEntity.ok(characterService.getAllCharacters(pageNumber, elementsByPage, orderBy, sortDir, name, edad, idMovie));

	}

	@GetMapping("/{id}")
	public ResponseEntity<CharacterResponseDTO> GetById(@PathVariable(name = "id") int id) {
		return ResponseEntity.ok(characterService.getCharacterById(id));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<CharacterResponseDTO> Post(@Valid @RequestBody CharacterCreationDTO characterCreationDTO) {
		return new ResponseEntity<>(characterService.createCharacter(characterCreationDTO), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<CharacterResponseDTO> Put(@Valid @RequestBody CharacterCreationDTO characterCreationDTO,
			@PathVariable(name = "id") int id) {
		return new ResponseEntity<>(characterService.updateCharacter(characterCreationDTO, id), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> Delete(@PathVariable(name = "id") int id) {
		characterService.deleteCharacter(id);
		return new ResponseEntity<>("Personaje eliminado con éxito", HttpStatus.OK);
	}
}
