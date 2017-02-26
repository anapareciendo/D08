
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
public abstract class Commentable extends DomainEntity {

	//	-------------------Attributes----------------------------------------
	public Collection<Comment> comments;
	
	@Valid
	@NotNull
	@OneToMany
	public abstract Collection<Comment> getComments();

	public abstract void setComments(Collection<Comment> comments);


}
