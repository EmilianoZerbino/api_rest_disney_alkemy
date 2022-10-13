package system.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import system.dto.cruddto.responsedto.ImageResponseDTO;
import system.services.interfaces.IImageService;

@RestController
@RequestMapping("/api/images")
public class ImageController {

	@Autowired
	private IImageService fileService;
	
	@GetMapping("/{filename:.+}")
	public ResponseEntity<Resource> getFile(@PathVariable String filename) throws Exception{
		Resource resource = fileService.load(filename);
				return ResponseEntity.ok()
					                 .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+resource.getFilename()+"\"")
					                 .body(resource);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<ImageResponseDTO> upoadFiles(@RequestParam("image") MultipartFile image) throws Exception{
	
			return new ResponseEntity<ImageResponseDTO>(fileService.save(image),HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{filename:.+}")
	public ResponseEntity<String> deleteFiles(@PathVariable String filename) throws Exception{

			return new ResponseEntity<String>(fileService.delete(filename),HttpStatus.OK);
		
	}
	
}
