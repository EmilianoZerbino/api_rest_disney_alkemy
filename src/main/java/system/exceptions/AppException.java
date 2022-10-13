package system.exceptions;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private HttpStatus state;
	private String message;

	public AppException(HttpStatus state, String message) {
		super();
		this.state = state;
		this.message = message;
	}

	public HttpStatus getEstado() {
		return state;
	}

	public void setEstado(HttpStatus estado) {
		this.state = estado;
	}

	public String getMensaje() {
		return message;
	}

	public void setMensaje(String mensaje) {
		this.message = mensaje;
	}

}
