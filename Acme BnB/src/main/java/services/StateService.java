package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.StateRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Property;
import domain.Request;
import domain.State;

@Service
@Transactional
public class StateService {

	//Managed repository
	@Autowired
	private StateRepository stateRepository;
	
	//Supporting services
	
	//Constructors
	public StateService() {
		super();
	}
	
	//Simple CRUD methods
	public State create(Property property, Request request) {
		Assert.notNull(property);
		Assert.notNull(request);
		State res;
		res = new State();
		res.setProperty(property);
		res.setRequest(request);

		return res;
	}
	
	public Collection<State> findAll() {
		Collection<State> res = stateRepository.findAll();
		return res;
	}

	public State findOne(int stateId) {
		State res = stateRepository.findOne(stateId);
		return res;
	}
	
	public State save(State state) {
		Assert.notNull(state, "The request to save cannot be null.");
		State res = stateRepository.save(state);
		res.getProperty().getStates().add(res);
		res.getRequest().getStates().add(res);
		return res;
	}
	
	public void delete(State state) {
		Authority b = new Authority();
		b.setAuthority(Authority.ADMIN);
		
		UserAccount ua=LoginService.getPrincipal();
		Assert.isTrue(ua.getAuthorities().contains(b), "You must to be an admin for this action");
		Assert.notNull(state, "The state to delete cannot be null.");
		Assert.isTrue(stateRepository.exists(state.getId()));
	
		stateRepository.delete(state);
	}
	
	//Utilites methods
	
	
}
