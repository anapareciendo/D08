package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SocialIdentity extends DomainEntity{

	//--------------------------Attributes-------------------------
	private String nick;
	private String nameSocial;
	private String urlSocial;
	
	@NotBlank
	@SafeHtml
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	@NotBlank
	@SafeHtml
	public String getNameSocial() {
		return nameSocial;
	}
	public void setNameSocial(String nameSocial) {
		this.nameSocial = nameSocial;
	}
	
	@NotBlank
	@URL
	@SafeHtml
	public String getUrlSocial() {
		return urlSocial;
	}
	public void setUrlSocial(String urlSocial) {
		this.urlSocial = urlSocial;
	}
	
	
	//-------------------------Relationships-------------------------
	private Actor actor;
	
	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Actor getActor() {
		return actor;
	}
	public void setActor(Actor actor){
		this.actor=actor;
	}
	
	
}
