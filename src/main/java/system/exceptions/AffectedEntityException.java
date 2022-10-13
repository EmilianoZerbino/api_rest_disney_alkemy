package system.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AffectedEntityException  extends RuntimeException{

	private static final long serialVersionUID = 1L;

	private String entityDeleteType;
	private String entityDeleteName;
	private String affectedEntityType;
	private String affectedEntityName;

	public AffectedEntityException(String entityDeleteType,String entityDeleteName,String affectedEntityType,String affectedEntityName) {
		super(String.format("Debe desafectar %s %s de %s %s antes de su eliminación.",entityDeleteType,entityDeleteName,affectedEntityType,affectedEntityName));
		this.entityDeleteType = entityDeleteType;
		this.entityDeleteName = entityDeleteName;
		this.affectedEntityType = affectedEntityType;
		this.affectedEntityName = affectedEntityName;
	}

	public String getEntityDeleteType() {
		return entityDeleteType;
	}

	public void setEntityDeleteType(String entityDeleteType) {
		this.entityDeleteType = entityDeleteType;
	}

	public String getEntityDeleteName() {
		return entityDeleteName;
	}

	public void setEntityDeleteName(String entityDeleteName) {
		this.entityDeleteName = entityDeleteName;
	}

	public String getAffectedEntityType() {
		return affectedEntityType;
	}

	public void setAffectedEntityType(String affectedEntityType) {
		this.affectedEntityType = affectedEntityType;
	}

	public String getAffectedEntityName() {
		return affectedEntityName;
	}

	public void setAffectedEntityName(String affectedEntityName) {
		this.affectedEntityName = affectedEntityName;
	}

}
