
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Property extends DomainEntity {

	//-------------------------------Attributes------------------
	private String	name;
	private Double	ratePerDay;
	private Double	totalRate;
	private String	address;
	private String	description;


	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotNull
	public Double getRatePerDay() {
		return ratePerDay;
	}

	public void setRatePerDay(Double ratePerDay) {
		this.ratePerDay = ratePerDay;
	}

	public Double getTotalRate() {
		return totalRate;
	}

	public void setTotalRate(Double totalRate) {
		this.totalRate = totalRate;
	}

	@NotBlank
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	//------------------------------Relationships------------------------------
	private Lessor lessor;
	private Collection<Audit> audits;
	private Collection<Request> requests;
	private Collection<Value> values;

	@Valid
	@NotNull
	@OneToMany(mappedBy="property")
	public Collection<Value> getValues() {
		return values;
	}
	public void setValues(Collection<Value> values) {
		this.values = values;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Lessor getLessor() {
		return lessor;
	}
	public void setLessor(Lessor lessor) {
		this.lessor = lessor;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy = "property")
	public Collection<Audit> getAudits() {
		return audits;
	}
	public void setAudits(Collection<Audit> audits) {
		this.audits = audits;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="property")
	public Collection<Request> getRequests() {
		return requests;
	}
	public void setRequests(Collection<Request> requests) {
		this.requests = requests;
	}
}
