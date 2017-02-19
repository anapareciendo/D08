
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Attribute extends DomainEntity {

	//-------------------------------Attributes------------------
	private String value;

	@NotBlank
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	//------------------------------Relationships-------------------------
	private Collection<Property> properties;

	@Valid
	@NotNull
	@ManyToMany(
			targetEntity=Property.class, 
			cascade={javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.MERGE}
			)
	@JoinTable(
			name="attribute_property",
			joinColumns=@JoinColumn(name="attribute_id"),
			inverseJoinColumns=@JoinColumn(name="property_id")
			)
	public Collection<Property> getProperties() {
		return properties;
	}
	public void setProperties(Collection<Property> properties) {
		this.properties = properties;
	}
	
}
