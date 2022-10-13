package system.dto.cruddto.responsedto;

import java.util.List;

public class GetAllResponseDTO {

	private List<?> content;
	private int pageNumber;
	private int elementsByPage;
	private long totalElements;
	private int totalPages;
	private boolean lastPage;

	public GetAllResponseDTO(List<?> content, int pageNumber, int elementsByPage, long totalElements, int totalPages,
			boolean lastPage) {
		
		this.content = content;
		this.pageNumber = pageNumber;
		this.elementsByPage = elementsByPage;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
		this.lastPage = lastPage;
		
	}

	public List<?> getContent() {
		return content;
	}

	public void setContent(List<?> content) {
		this.content = content;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getElementsByPage() {
		return elementsByPage;
	}

	public void setElementsByPage(int elementsByPage) {
		this.elementsByPage = elementsByPage;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public boolean isLastPage() {
		return lastPage;
	}

	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}

}
