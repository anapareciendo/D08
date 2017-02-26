
package domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Auditor extends Actor {

	//---------------------------Attributes-------------------------
	private String company;

	@NotBlank
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	//---------------------------Relationships--------------------------
	private Collection<Audit> audits;
	
	@Valid
	@NotNull
	@OneToMany(mappedBy = "auditor")
	public Collection<Audit> getAudits() {
		return audits;
	}
	public void setAudits(Collection<Audit> audits) {
		this.audits = audits;
	}
	
	public Collection<Comment> getComments() {
		return new ArrayList<Comment>();
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = new ArrayList<Comment>();
	}
	
	

}
