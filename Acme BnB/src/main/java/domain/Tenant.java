
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Tenant extends Actor {

	//---------------------------Relationships--------------------------
	private Collection<Finder> finders;
	private Collection<Request> requests;
	private Collection<Invoice> invoices;
	
	@Valid
	@NotNull
	@OneToMany(mappedBy = "tenant")
	public Collection<Finder> getFinders() {
		return finders;
	}
	public void setFinders(Collection<Finder> finders) {
		this.finders = finders;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy = "tenant")
	public Collection<Request> getRequests() {
		return requests;
	}
	public void setRequests(Collection<Request> requests) {
		this.requests = requests;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy = "tenant")
	public Collection<Invoice> getInvoices() {
		return invoices;
	}
	public void setInvoices(Collection<Invoice> invoices) {
		this.invoices = invoices;
	}
}
