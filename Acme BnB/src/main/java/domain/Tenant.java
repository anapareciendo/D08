
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
	
	public Collection<Comment> getComments() {
		return comments;
	}
	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}
	
}
