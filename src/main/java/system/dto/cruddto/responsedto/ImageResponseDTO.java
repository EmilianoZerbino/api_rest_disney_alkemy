package system.dto.cruddto.responsedto;

public class ImageResponseDTO {

	private String imageName;
	private String imageUrl;
	
	public ImageResponseDTO(String name, String fileUrl) {
		super();
		this.imageName = name;
		this.imageUrl = fileUrl;
	}
	
	public String getName() {
		return imageName;
	}
	
	public void setName(String name) {
		this.imageName = name;
	}
	
	public String getFileUrl() {
		return imageUrl;
	}
	
	public void setFileUrl(String fileUrl) {
		this.imageUrl = fileUrl;
	}
	
	
}
