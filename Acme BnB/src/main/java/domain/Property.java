
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

	@NotNull
	public Double getTotalRate() {
		return totalRate;
	}

	public void setTotalRate(Double totalRate) {
		this.totalRate = totalRate;
	}

	@NotNull
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@NotNull
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	//------------------------------Relationships------------------------------
	private Lessor lessor;
	private Collection<Attribute> attributes;
	private Collection<State> states;
	private Collection<Audit> audits;

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
	public Collection<Attribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(Collection<Attribute> attributes) {
		this.attributes = attributes;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy = "property")
	public Collection<State> getStates() {
		return states;
	}
	public void setStates(Collection<State> states) {
		this.states = states;
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
}
