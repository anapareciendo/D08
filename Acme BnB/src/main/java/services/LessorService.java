
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.LessorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Comment;
import domain.Lessor;
import domain.Property;
import domain.SocialIdentity;
import forms.ActorForm;

@Service
@Transactional
public class LessorService {

	//Managed repository
	@Autowired
	private LessorRepository lessorRepository;


	//Supporting services
	@Autowired
	private UserAccountService userAccountService;
	
	//Validator
	@Autowired
	private Validator validator;

	//Constructors
	public LessorService() {
		super();
	}

	//Simple CRUD methods
	public Lessor create(UserAccount ua) {
		Lessor res;
		res = new Lessor();
		res.setPostComments(new ArrayList<Comment>());
		res.setSocialIdentities(new ArrayList<SocialIdentity>());
		res.setProperties(new ArrayList<Property>());
		res.setComments(new ArrayList<Comment>());
		res.setAmount(0.0);
		res.setUserAccount(ua);
		
		return res;
	}

	public Collection<Lessor> findAll() {
		Collection<Lessor> res = lessorRepository.findAll();
		return res;
	}

	public Lessor findOne(int lessorId) {
		Lessor res = lessorRepository.findOne(lessorId);
		return res;
	}

	public Lessor save(Lessor lessor) {
		Assert.notNull(lessor, "The lessor to save cannot be null.");
		Lessor res = lessorRepository.save(lessor);

		return res;
	}

	public void delete(Lessor lessor) {
		UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(ua.getAuthorities().contains(a), "You must to be a admin to delete an actor.");

		Assert.notNull(lessor, "The lessor to delete cannot be null.");
		Assert.isTrue(lessorRepository.exists(lessor.getId()));

		Assert.isNull(lessor.getPostComments().isEmpty(), "The lessor cannot be delete with post comments");
		Assert.isTrue(lessor.getComments().isEmpty(), "The lessor cannot be delete with recive comments");
		Assert.isTrue(lessor.getSocialIdentities().isEmpty(), "The lessor cannot be delete with social identities");
		Assert.isTrue(lessor.getProperties().isEmpty(), "The lessor cannot be delete with properties");

		lessorRepository.delete(lessor);
	}

	//Utilites methods
	public Lessor findByUserAccountId(int id){
		Assert.notNull(id);
		return lessorRepository.findByUserAccountId(id);
	}
	
	public Collection<Lessor> findMyLessors(int id){
		Assert.notNull(id);
		return lessorRepository.findMyLessors(id);
	}
	
	public Lessor reconstruct(ActorForm actor, BindingResult binding){
		Lessor result;
		List<String> cond = Arrays.asList(actor.getConditions());
		if(!actor.getPassword1().isEmpty() && !actor.getPassword2().isEmpty() && actor.getPassword1().equals(actor.getPassword2()) && cond.contains("acepto")){
			UserAccount ua = userAccountService.create();
			
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			String hash = encoder.encodePassword(actor.getPassword1(), null);
			
			ua.setUsername(actor.getUsername());
			ua.setPassword(hash);
			
			Authority a = new Authority();
			a.setAuthority(Authority.LESSOR);
			ua.getAuthorities().add(a);
			
			result=this.create(ua);
			
			result.setName(actor.getName());
			result.setSurname(actor.getSurname());
			result.setEmail(actor.getEmail());
			result.setPhone(actor.getPhone());
			result.setPicture(actor.getPicture());
			validator.validate(result, binding);
		}else{
			result=new Lessor();
			result.setName("Pass");
			if(!cond.contains("acepto")){
				result.setName("Cond");
			}
		}
		return result;
	}
	
	public Lessor reconstruct2(Lessor lessor, BindingResult binding) {
		Lessor res;
		
		if(lessor.getId()==0){
			res = this.create(LoginService.getPrincipal());
		}else{
			res = lessorRepository.findOne(lessor.getId());
		}
		res.setName(lessor.getName());
		res.setSurname(lessor.getSurname());
		res.setEmail(lessor.getEmail());
		res.setPhone(lessor.getPhone());
		res.setPicture(lessor.getPicture());
		validator.validate(res, binding);
		return res;
	}
	


	public Lessor reconstruct3(Lessor lessor, BindingResult binding) {
		Lessor res;
		
		if(lessor.getId()==0){
			res = this.create(LoginService.getPrincipal());
		}else{
			res = lessorRepository.findOne(lessor.getId());
		}
		res.getCreditCard().setBrand(lessor.getCreditCard().getBrand());
		res.getCreditCard().setCvv(lessor.getCreditCard().getCvv());
		res.getCreditCard().setExpirationMonth(lessor.getCreditCard().getExpirationMonth());
		res.getCreditCard().setExpirationYear(lessor.getCreditCard().getExpirationYear());
		res.getCreditCard().setHolder(lessor.getCreditCard().getHolder());
		res.getCreditCard().setNumber(lessor.getCreditCard().getNumber());
		validator.validate(res, binding);
		return res;
	}
}
