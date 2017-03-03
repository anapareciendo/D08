package services;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RequestRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Property;
import domain.Request;
import domain.Status;
import domain.Tenant;
import forms.RequestForm;

@Service
@Transactional
public class RequestService {

	//Managed repository
	@Autowired
	private RequestRepository requestRepository;
	
	//Supporting services
	@Autowired
	private TenantService tenantService;
	@Autowired
	private PropertyService propertyService;
	
	@Autowired
	private Validator validator;
	
	//Constructors
	public RequestService() {
		super();
	}
	
	//Simple CRUD methods
	public Request create(Tenant tenant, Property property) {
		Assert.notNull(tenant);
		Request res;
		res = new Request();
		res.setTenant(tenant);
		res.setProperty(property);
		res.setStatus(Status.PENDING);
		res.setCreditCard(tenant.getCreditCard());

		return res;
	}
	
	public Collection<Request> findAll() {
		Collection<Request> res = requestRepository.findAll();
		return res;
	}

	public Request findOne(int requestId) {
		Request res = requestRepository.findOne(requestId);
		return res;
	}
	
	public Request save(Request request) {
		Assert.notNull(request, "The request to save cannot be null.");
		Request res = requestRepository.save(request);
		res.getTenant().getRequests().add(res);
		res.getProperty().getRequests().add(res);
		return res;
	}
	
	public void delete(Request request) {
		Authority b = new Authority();
		b.setAuthority(Authority.ADMIN);
		
		UserAccount ua=LoginService.getPrincipal();
		Assert.isTrue(request.getTenant().getUserAccount().equals(ua) || ua.getAuthorities().contains(b), "You are not the owner of this request");
		Assert.notNull(request, "The request to delete cannot be null.");
		Assert.isTrue(requestRepository.exists(request.getId()));
	
		requestRepository.delete(request);
	}
	
	//Utilites methods
	public Collection<Request> findMyRequest(){
		Authority b = new Authority();
		b.setAuthority(Authority.TENANT);
		UserAccount ua=LoginService.getPrincipal();
		Assert.isTrue(ua.getAuthorities().contains(b), "You must to be a tenant for this action");
		return requestRepository.findMyRequest(ua.getId());
	}
	
	public Collection<Request> findMyRequestAcceptProperties(){
		Authority b = new Authority();
		b.setAuthority(Authority.LESSOR);
		UserAccount ua=LoginService.getPrincipal();
		Assert.isTrue(ua.getAuthorities().contains(b), "You must to be a lessor for this action");
		return requestRepository.findMyRequestAcceptProperties(ua.getId());
	}
	
	public Collection<Request> findMyRequestDeniedProperties(){
		Authority b = new Authority();
		b.setAuthority(Authority.LESSOR);
		UserAccount ua=LoginService.getPrincipal();
		Assert.isTrue(ua.getAuthorities().contains(b), "You must to be a lessor for this action");
		return requestRepository.findMyRequestDeniedProperties(ua.getId());
	}

	public Request reconstruct(RequestForm request, BindingResult binding) {
		Request res;
		List<String> smoker = Arrays.asList(request.getSmoker());
		res=this.create(tenantService.findByUserAccountId(LoginService.getPrincipal().getId()), propertyService.findOne(request.getPropertyId()));
		res.setCheckIn(request.getCheckIn());
		res.setCheckOut(request.getCheckOut());
		if(smoker.contains("smoker")){
			res.setSmoke(true);
		}else{
			res.setSmoke(false);
		}
		validator.validate(res, binding);
		return res;
	}
	
	
}
