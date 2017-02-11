package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class State extends DomainEntity{
	
	//---------------------------------Attributes----------------------------
	private Status status;

	@Valid
	@NotNull
	public Status getStatus() {
		return status;
	}

	
	public void setStatus(Status status) {
		this.status = status;
	}

	//------------------------------Relationships-------------------------
	private Property property;
	private Request request;
	
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Property getProperty() {
		return property;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Request getRequest() {
		return request;
	}
	
}
