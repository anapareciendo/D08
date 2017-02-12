package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Request;
import domain.State;
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
	public Request create(Tenant tenant) {
		Assert.notNull(tenant);
		Request res;
		res = new Request();
		res.setTenant(tenant);
		res.setStates(new ArrayList<State>());

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
		return res;
	}
	
	public void delete(Request request) {
		Authority b = new Authority();
		b.setAuthority(Authority.ADMIN);
		
		UserAccount ua=LoginService.getPrincipal();
		Assert.isTrue(request.getTenant().getUserAccount().equals(ua) || ua.getAuthorities().contains(b), "You are not the owner of this request");
		Assert.notNull(request, "The campaign to delete cannot be null.");
		Assert.isTrue(requestRepository.exists(request.getId()));
	
		Assert.isTrue(request.getStates().isEmpty(), "The request cannot be delete with states");
		
		requestRepository.delete(request);
	}
	
	//Utilites methods
	
	
}
