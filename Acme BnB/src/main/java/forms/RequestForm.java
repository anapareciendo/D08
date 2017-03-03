package forms;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;

@Access(AccessType.PROPERTY)
public class RequestForm {

	private int propertyId;
	
	private Date checkIn;
	private Date checkOut;
	
	public Date getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}

	public Date getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}

	private String[] smoker={};

	public RequestForm() {
		super();
	}

	public String[] getSmoker() {
		return smoker;
	}

	public void setSmoker(String[] smoker) {
		this.smoker = smoker;
	}
	
	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	
}
