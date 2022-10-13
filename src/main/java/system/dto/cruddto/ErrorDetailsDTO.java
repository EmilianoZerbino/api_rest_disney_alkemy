package system.dto.cruddto;

import java.util.Date;

public class ErrorDetailsDTO {

	private Date TimeMark;
	private String message;
	private String details;
	
	public ErrorDetailsDTO(Date timeMark, String message, String details) {
		super();
		TimeMark = timeMark;
		this.message = message;
		this.details = details;
	}
	
	public Date getTimeMark() {
		return TimeMark;
	}
	public void setTimeMark(Date timeMark) {
		TimeMark = timeMark;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
}
