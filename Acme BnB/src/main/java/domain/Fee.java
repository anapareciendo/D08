package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class Fee extends DomainEntity {

	//---------------Attributes-----------
	private double cost;
	
	@Min(0)
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	
}
