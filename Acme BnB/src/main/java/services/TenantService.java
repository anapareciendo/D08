
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

import repositories.TenantRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Comment;
import domain.Request;
import domain.SocialIdentity;
import domain.Tenant;
import forms.ActorForm;

@Service
@Transactional
public class TenantService {

	//Managed repository
	@Autowired
	private TenantRepository tenantRepository;


	//Supporting services
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private Validator validator;

	//Constructors
	public TenantService() {
		super();
	}

	//Simple CRUD methods
	public Tenant create(UserAccount ua) {
		Tenant res;
		res = new Tenant();
		res.setRequests(new ArrayList<Request>());
		res.setSocialIdentities(new ArrayList<SocialIdentity>());
		res.setComments(new ArrayList<Comment>());
		res.setPostComments(new ArrayList<Comment>());
		res.setUserAccount(ua);

		return res;
	}

	public Collection<Tenant> findAll() {
		Collection<Tenant> res = tenantRepository.findAll();
		return res;
	}

	public Tenant findOne(int tenantId) {
		Tenant res = tenantRepository.findOne(tenantId);
		return res;
	}

	public Tenant save(Tenant tenant) {
		Assert.notNull(tenant, "The tenant to save cannot be null.");
		Tenant res = tenantRepository.save(tenant);

		return res;
	}

	public void delete(Tenant tenant) {
		UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(ua.getAuthorities().contains(a), "You must to be a admin to delete an actor.");

		Assert.notNull(tenant, "The tenant to delete cannot be null.");
		Assert.isTrue(tenantRepository.exists(tenant.getId()));

		Assert.isNull(tenant.getFinder(), "The tenant cannot be delete with finder");
		Assert.isTrue(tenant.getRequests().isEmpty(), "The tenant cannot be delete with requests");
		Assert.isTrue(tenant.getComments().isEmpty(), "The tenant cannot be delete with comments");
		Assert.isTrue(tenant.getPostComments().isEmpty(), "The tenant cannot be delete with post comments");
		Assert.isTrue(tenant.getSocialIdentities().isEmpty(), "The tenant cannot be delete with social identites");
		
		
		tenantRepository.delete(tenant);
	}

	//Utilites methods
	public Tenant findByUserAccountId(int id){
		Assert.notNull(id);
		return tenantRepository.findByUserAccountId(id);
	}

	public Collection<Tenant> findMyTenants(int id){
		Assert.notNull(id);
		return tenantRepository.findMyTenants(id);
	}

	
	public Tenant reconstruct(ActorForm actor, BindingResult binding) {
		Tenant result;
		List<String> cond = Arrays.asList(actor.getConditions());
		if(!actor.getPassword1().isEmpty() && !actor.getPassword2().isEmpty() && actor.getPassword1().equals(actor.getPassword2()) && cond.contains("acepto")){
			UserAccount ua = userAccountService.create();
			
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			String hash = encoder.encodePassword(actor.getPassword1(), null);
			
			ua.setUsername(actor.getUsername());
			ua.setPassword(hash);
			
			Authority a = new Authority();
			a.setAuthority(Authority.TENANT);
			ua.getAuthorities().add(a);
			
			result=this.create(ua);
			
			result.setName(actor.getName());
			result.setSurname(actor.getSurname());
			result.setEmail(actor.getEmail());
			result.setPhone(actor.getPhone());
			result.setPicture(actor.getPicture());
			validator.validate(result, binding);
		}else{
			result=new Tenant();
			result.setName("Pass");
			if(!cond.contains("acepto")){
				result.setName("Cond");
			}
		}
		return result;
	}
	
	public Tenant reconstruct(Tenant tenant, BindingResult binding) {
		Tenant res;
		
		if(tenant.getId()==0){
			res = this.create(LoginService.getPrincipal());
		}else{
			res = tenantRepository.findOne(tenant.getId());
		}
		res.setName(tenant.getName());
		res.setSurname(tenant.getSurname());
		res.setEmail(tenant.getEmail());
		res.setPhone(tenant.getPhone());
		res.setPicture(tenant.getPicture());
		validator.validate(res, binding);
		return res;
	}

	public Tenant reconstruct2(Tenant tenant, BindingResult binding) {
		Tenant res;
		
		if(tenant.getId()==0){
			res = this.create(LoginService.getPrincipal());
		}else{
			res = tenantRepository.findOne(tenant.getId());
		}
		res.getCreditCard().setBrand(tenant.getCreditCard().getBrand());
		res.getCreditCard().setCvv(tenant.getCreditCard().getCvv());
		res.getCreditCard().setExpirationMonth(tenant.getCreditCard().getExpirationMonth());
		res.getCreditCard().setExpirationYear(tenant.getCreditCard().getExpirationYear());
		res.getCreditCard().setHolder(tenant.getCreditCard().getHolder());
		res.getCreditCard().setNumber(tenant.getCreditCard().getNumber());
		validator.validate(res, binding);
		return res;
	}
}
