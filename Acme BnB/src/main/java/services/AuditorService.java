
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

import repositories.AuditorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Auditor;
import domain.Comment;
import domain.SocialIdentity;
import forms.ActorForm;

@Service
@Transactional
public class AuditorService {

	//Managed repository
	@Autowired
	private AuditorRepository auditorRepository;
	
	//Validator
		@Autowired
		private Validator validator;


	//Supporting services
	@Autowired
	private UserAccountService userAccountService;

	//Constructors
	public AuditorService() {
		super();
	}

	//Simple CRUD methods
	public Auditor create(UserAccount ua) {
		Auditor res;
		res = new Auditor();
		res.setPostComments(new ArrayList<Comment>());
		res.setSocialIdentities(new ArrayList<SocialIdentity>());
		res.setComments(new ArrayList<Comment>());
		res.setUserAccount(ua);
		return res;
	}

	public Collection<Auditor> findAll() {
		Collection<Auditor> res = auditorRepository.findAll();
		return res;
	}

	public Auditor findOne(int auditorId) {
		Auditor res = auditorRepository.findOne(auditorId);
		return res;
	}

	public Auditor save(Auditor auditor) {
		Assert.notNull(auditor, "The auditor to save cannot be null.");
		Auditor res = auditorRepository.save(auditor);

		return res;
	}

	public void delete(Auditor auditor) {
		UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(ua.getAuthorities().contains(a), "You must to be a admin to delete an actor.");

		Assert.notNull(auditor, "The adminstrator to delete cannot be null.");
		Assert.isTrue(auditorRepository.exists(auditor.getId()));

		Assert.isNull(auditor.getPostComments().isEmpty(), "The administrator cannot be delete with post comments");
		Assert.isTrue(auditor.getSocialIdentities().isEmpty(), "The administrator cannot be delete with social identities");

		auditorRepository.delete(auditor);
	}

	//Utilites methods
	public Auditor findByUserAccountId(int id){
		Assert.notNull(id);
		return auditorRepository.findByUserAccountId(id);
	}
	
	public Auditor reconstruct(Auditor auditor, BindingResult binding) {
		Auditor res;
		
		if(auditor.getId()==0){
			res = this.create(LoginService.getPrincipal());
		}else{
			res = auditorRepository.findOne(auditor.getId());
		}
		res.setName(auditor.getName());
		res.setSurname(auditor.getSurname());
		res.setEmail(auditor.getEmail());
		res.setPhone(auditor.getPhone());
		res.setPicture(auditor.getPicture());
		res.setCompany(auditor.getCompany());
		validator.validate(res, binding);
		return res;
	}
	
	public Auditor reconstructForm(ActorForm actor, BindingResult binding) {
		Auditor result;
		List<String> cond = Arrays.asList(actor.getConditions());
		if(!actor.getPassword1().isEmpty() && !actor.getPassword2().isEmpty() && actor.getPassword1().equals(actor.getPassword2()) && cond.contains("acepto")){
			UserAccount ua = userAccountService.create();
			
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			String hash = encoder.encodePassword(actor.getPassword1(), null);
			
			ua.setUsername(actor.getUsername());
			ua.setPassword(hash);
			
			Authority a = new Authority();
			a.setAuthority(Authority.AUDITOR);
			ua.getAuthorities().add(a);
			
			result=this.create(ua);
			
			result.setName(actor.getName());
			result.setSurname(actor.getSurname());
			result.setEmail(actor.getEmail());
			result.setPhone(actor.getPhone());
			result.setPicture(actor.getPicture());
			validator.validate(result, binding);
		}else{
			result=new Auditor();
			result.setName("Pass");
			if(!cond.contains("acepto")){
				result.setName("Cond");
			}
		}
		return result;
	}

}
