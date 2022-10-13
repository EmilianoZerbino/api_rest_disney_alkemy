package system.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import system.dto.cruddto.ErrorDetailsDTO;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetailsDTO> ResourceNotFoundController(ResourceNotFoundException exception,WebRequest webRequest){
		ErrorDetailsDTO errorDetailsDTO = new ErrorDetailsDTO(new Date(), exception.getMessage(),webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetailsDTO,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoCharactersException.class)
	public ResponseEntity<ErrorDetailsDTO> NoCharactersExceptionController(NoCharactersException exception,WebRequest webRequest){
		ErrorDetailsDTO errorDetailsDTO = new ErrorDetailsDTO(new Date(), exception.getMessage(),webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetailsDTO,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AffectedEntityException.class)
	public ResponseEntity<ErrorDetailsDTO> AffectedEntityExceptionController(AffectedEntityException exception,WebRequest webRequest){
		ErrorDetailsDTO errorDetailsDTO = new ErrorDetailsDTO(new Date(), exception.getMessage(),webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetailsDTO,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ErrorDetailsDTO> handleMaxSizeException(MaxUploadSizeExceededException exception,WebRequest webRequest){
		ErrorDetailsDTO errorDetailsDTO = new ErrorDetailsDTO(new Date(), exception.getMessage(),webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetailsDTO,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetailsDTO> GlobalExceptionController(Exception exception,WebRequest webRequest){
		ErrorDetailsDTO errorDetailsDTO = new ErrorDetailsDTO(new Date(), exception.getMessage(),webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetailsDTO,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, 
			  HttpHeaders headers, HttpStatus status, WebRequest request){
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
			});
		return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);		
	}
}