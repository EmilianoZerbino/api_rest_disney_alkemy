package system.services.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import system.dto.cruddto.responsedto.ImageResponseDTO;

public interface IImageService {

	public ImageResponseDTO save(MultipartFile file) throws Exception;
	public Resource load (String name) throws Exception;
	public String delete (String name) throws Exception;
}
