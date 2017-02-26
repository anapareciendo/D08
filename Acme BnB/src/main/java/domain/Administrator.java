package domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Administrator extends Actor{

	public Collection<Comment> getComments() {
		return new ArrayList<Comment>();
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = new ArrayList<Comment>();
		
	}
	
}
