package system.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private String resourceName;
	private String fieldName;
	private int intFieldValue;
	private String stringFieldValue;
	
	public ResourceNotFoundException(String resourceName, String fieldName, int intFieldValue) {
		super(String.format("%s No encontrado con: %s : '%s'",resourceName, fieldName, intFieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.intFieldValue = intFieldValue;
	}
	
	public ResourceNotFoundException(String resourceName, String fieldName, String stringFieldValue) {
		super(String.format("%s No encontrado con: %s : '%s'",resourceName, fieldName, stringFieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.stringFieldValue = stringFieldValue;
	}

	public int getIntFieldValue() {
		return intFieldValue;
	}

	public void setIntFieldValue(int intFieldValue) {
		this.intFieldValue = intFieldValue;
	}

	public String getStringFieldValue() {
		return stringFieldValue;
	}

	public void setStringFieldValue(String stringFieldValue) {
		this.stringFieldValue = stringFieldValue;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

}
