package system.services.interfaces;

import system.dto.cruddto.creationdto.CharacterCreationDTO;
import system.dto.cruddto.responsedto.CharacterResponseDTO;
import system.dto.cruddto.responsedto.GetAllResponseDTO;

public interface ICharacterService {

	public GetAllResponseDTO getAllCharacters(int pageNumber, int elementsByPage, String orderBy, String sortDir, String name, Integer age, Integer idMovie);
	public CharacterResponseDTO getCharacterById(int id);
	public CharacterResponseDTO createCharacter(CharacterCreationDTO characterCreationDTO);
	public CharacterResponseDTO updateCharacter(CharacterCreationDTO characterCreationDTO, int id);
	public void deleteCharacter(int id);
}
