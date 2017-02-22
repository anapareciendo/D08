package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ValueRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Attribute;
import domain.Property;
import domain.Value;

@Service
@Transactional
public class ValueService {

	//Managed repository
	@Autowired
	private ValueRepository valueRepository;
	
	//Supporting services
	
	//Constructors
	public ValueService() {
		super();
	}
	
	//Simple CRUD methods
	public Value create(Property property, Attribute attribute) {
		Value res;
		res = new Value();
		res.setAttribute(attribute);
		res.setProperty(property);
		
		return res;
	}
	
	public Collection<Value> findAll() {
		Collection<Value> res = valueRepository.findAll();
		return res;
	}

	public Value findOne(int valueId) {
		Value res = valueRepository.findOne(valueId);
		return res;
	}
	
	public Value save(Value value) {
		Assert.notNull(value, "The property to save cannot be null.");
		Value res = valueRepository.save(value);
		res.getAttribute().getValues().add(res);
		res.getProperty().getValues().add(res);
		return res;
	}
	
	public void delete(Value value) {
		Assert.notNull(value, "The value to delete cannot be null.");
		Assert.isTrue(valueRepository.exists(value.getId()));
		
		Authority b = new Authority();
		b.setAuthority(Authority.ADMIN);

		UserAccount ua=LoginService.getPrincipal();
		Assert.isTrue(ua.getAuthorities().contains(b) || value.getProperty().getLessor().getUserAccount().equals(ua), "You are not the owner of this value");
		
		valueRepository.delete(value);
	}
	
	
	//Utilites methods
	
}

