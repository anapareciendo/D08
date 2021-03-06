package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PropertyRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Audit;
import domain.Lessor;
import domain.Property;
import domain.Request;
import domain.Value;

@Service
@Transactional
public class PropertyService {

	//Managed repository
	@Autowired
	private PropertyRepository propertyRepository;
	
	//Supporting services
	@Autowired
	private LessorService lessorService;
	
	@Autowired
	private Validator validator;
	
	//Constructors
	public PropertyService() {
		super();
	}
	
	//Simple CRUD methods
	public Property create(Lessor lessor) {
		Assert.notNull(lessor);
		Property res;
		res = new Property();
		res.setLessor(lessor);
		res.setValues(new ArrayList<Value>());
		res.setAudits(new ArrayList<Audit>());
		res.setRequests(new ArrayList<Request>());

		return res;
	}
	
	public Collection<Property> findAll() {
		Collection<Property> res = propertyRepository.findAll();
		return res;
	}

	public Property findOne(int propertyId) {
		Property res = propertyRepository.findOne(propertyId);
		return res;
	}
	
	public Property save(Property property) {
		Assert.notNull(property, "The property to save cannot be null.");
		Property res = propertyRepository.save(property);
		res.getLessor().getProperties().add(res);
		return res;
	}
	
	public void delete(Property property) {
		Assert.notNull(property, "The property to delete cannot be null.");
		Assert.isTrue(propertyRepository.exists(property.getId()));
		
		Authority b = new Authority();
		b.setAuthority(Authority.ADMIN);
		UserAccount ua=LoginService.getPrincipal();
		
		Assert.isTrue(property.getLessor().getUserAccount().equals(ua) || ua.getAuthorities().contains(b), "You are not the owner of the property");
		Assert.isTrue(property.getValues().isEmpty(), "The property cannot be delete with attributes");
		Assert.isTrue(property.getAudits().isEmpty(), "The property cannot be delete with audits");
		Assert.isTrue(property.getRequests().isEmpty(), "The property cannot be delete with request");
		
		propertyRepository.delete(property);
	}
	
	
	//Utilites methods
	public Collection<Property> findMyProperties(){
		Authority b = new Authority();
		b.setAuthority(Authority.LESSOR);
		UserAccount ua=LoginService.getPrincipal();
		Assert.isTrue(ua.getAuthorities().contains(b), "You must to be a lessor for this action");
		return propertyRepository.findMyProperties(ua.getId());
	}

	public Property reconstruct(Property property, BindingResult binding) {
		Property res;
		
		if(property.getId()==0){
			res = this.create(lessorService.findByUserAccountId(LoginService.getPrincipal().getId()));
		}else{
			res = propertyRepository.findOne(property.getId());
		}
		res.setName(property.getName());
		res.setRatePerDay(property.getRatePerDay());
		res.setDescription(property.getDescription());
		res.setAddress(property.getAddress());
		validator.validate(res, binding);
		return res;
	}
	
	public Collection<Property> findAllSortedRequest(){
		return propertyRepository.findAllSortedRequest();
	}
	
	public Collection<Property> findNotAuditProperty(){
		Authority b = new Authority();
		b.setAuthority(Authority.AUDITOR);
		UserAccount ua=LoginService.getPrincipal();
		Assert.isTrue(ua.getAuthorities().contains(b), "You must to be a auditor for this action");
		return propertyRepository.findNotAuditProperty(ua.getId());
	}
	
}

