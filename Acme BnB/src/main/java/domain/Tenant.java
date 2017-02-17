
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Tenant extends Actor {
	
	//----------------------------Attributes----------------------------
	private CreditCard creditCard;
	
	@Valid
	@NotNull
	public CreditCard getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	//---------------------------Relationships--------------------------
	private Finder finder;
	private Collection<Request> requests;
	private Collection<Invoice> invoices;
	
	@Valid
	@OneToOne(mappedBy="tenant",optional=true)
	@JoinColumn(name="finder_id", referencedColumnName="finder_id")
	public Finder getFinder() {
		return finder;
	}
	public void setFinder(Finder finder) {
		this.finder = finder;
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
