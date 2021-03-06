
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends Commentable {

	//	-------------------Attributes----------------------------------------
	private String	name;
	private String	surname;
	private String	email;
	private String	phone;
	private String	picture;


	@NotBlank
	@SafeHtml
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}

	@NotBlank
	@Email
	@SafeHtml
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Pattern(regexp = "([+][0-9]{3})[ ]*([(][0-9]{3}[)])?[ ]*([0-9a-zA-Z][ -]*){4,}")
	@SafeHtml
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@URL
	@NotBlank
	@SafeHtml
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}


	//----------------Relationships------------------------------------------
	private UserAccount					userAccount;
	private Collection<SocialIdentity>	socialIdentities;
	private Collection<Comment>			postComments;

	@Valid
	@NotNull
	@OneToOne(cascade = javax.persistence.CascadeType.ALL)
	public UserAccount getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "actor")
	public Collection<SocialIdentity> getSocialIdentities() {
		return socialIdentities;
	}
	public void setSocialIdentities(Collection<SocialIdentity> socialIdentities) {
		this.socialIdentities = socialIdentities;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "sender")
	public Collection<Comment> getPostComments() {
		return postComments;
	}
	public void setPostComments(Collection<Comment> postComments) {
		this.postComments = postComments;
	}
	
	
}
