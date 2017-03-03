package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Property;
import domain.Request;
import domain.Tenant;

@Service
@Transactional
public class RequestService {

	//Managed repository
	@Autowired
	private RequestRepository requestRepository;
	
	//Supporting services
	
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
	
	public Collection<Request> findMyRequestProperties(){
		Authority b = new Authority();
		b.setAuthority(Authority.LESSOR);
		UserAccount ua=LoginService.getPrincipal();
		Assert.isTrue(ua.getAuthorities().contains(b), "You must to be a lessor for this action");
		return requestRepository.findMyRequestProperties(ua.getId());
	}
	
	
}
