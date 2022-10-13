package system.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import system.controllers.ImageController;
import system.dto.cruddto.responsedto.ImageResponseDTO;
import system.exceptions.ResourceNotFoundException;
import system.services.interfaces.IImageService;

@Service
public class ImageService implements IImageService {

	private final Path rootFolder = Paths.get("images");

	@Override
	public ImageResponseDTO save(MultipartFile file) throws Exception {

		int i;
		boolean check = true;
		for (i = file.getOriginalFilename().length() - 1; check; i--) {
			if (file.getOriginalFilename().charAt(i) == '.') {
				check = false;
			}
		}
		String extension = file.getOriginalFilename().substring(i + 1);

		check = true;
		i = 1;

		while (check) {
			Resource resource = load(i + extension);
			if (resource.exists()) {
				i++;
			} else {
				check = false;
			}
		}

		Files.copy(file.getInputStream(), this.rootFolder.resolve(i + extension));

		String filename = i + extension;
		String url = MvcUriComponentsBuilder
				.fromMethodName(ImageController.class, "getFile", (i + extension).toString()).build().toString();

		return new ImageResponseDTO(filename, url);
	}

	@Override
	public Resource load(String name) throws Exception {
		Path file = rootFolder.resolve(name);
		Resource resource = new UrlResource(file.toUri());
		return resource;
	}

	@Override
	public String delete(String name) throws Exception {
		Path file = rootFolder.resolve(name);
		try {
			Files.delete(file);
			return "Imagen eliminada con éxito";
		} catch (Exception ex) {
			throw new ResourceNotFoundException("Imagen", "filename", name);
		}
			
	}
}
