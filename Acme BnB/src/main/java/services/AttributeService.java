package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AttributeRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Attribute;
import domain.Value;

@Service
@Transactional
public class AttributeService {

	//Managed repository
	@Autowired
	private AttributeRepository attributeRepository;
	
	//Supporting services
	@Autowired
	private Validator validator;
	
	//Constructors
	public AttributeService() {
		super();
	}
	
	//Simple CRUD methods
	public Attribute create() {
		Attribute res;
		res = new Attribute();
		res.setValues(new ArrayList<Value>());
		return res;
	}
	
	public Collection<Attribute> findAll() {
		Collection<Attribute> res = attributeRepository.findAll();
		return res;
	}

	public Attribute findOne(int attributeId) {
		Attribute res = attributeRepository.findOne(attributeId);
		return res;
	}
	
	public Attribute save(Attribute attribute) {
		Assert.notNull(attribute, "The property to save cannot be null.");
		Attribute res = attributeRepository.save(attribute);
		return res;
	}
	
	public void delete(Attribute attribute) {
		Assert.notNull(attribute, "The property to delete cannot be null.");
		Assert.isTrue(attributeRepository.exists(attribute.getId()));
		
		Authority b = new Authority();
		b.setAuthority(Authority.ADMIN);

		UserAccount ua=LoginService.getPrincipal();
		Assert.isTrue(ua.getAuthorities().contains(b), "You must to be an admin for this action");
		Assert.isTrue(attribute.getValues().isEmpty(), "The attribute cannot be delete with values");
	
		
		attributeRepository.delete(attribute);
	}

	public Attribute reconstruct(Attribute attribute, BindingResult binding) {
		Attribute res;
		
		if(attribute.getId()==0){
			res = this.create();
		}else{
			res = attributeRepository.findOne(attribute.getId());
		}
		res.setName(attribute.getName());
		validator.validate(res, binding);
		
		return res;
	}
	
	
	//Utilites methods
	
}

