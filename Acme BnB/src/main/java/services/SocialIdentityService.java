package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SocialIdentityRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Auditor;
import domain.Lessor;
import domain.SocialIdentity;
import domain.Tenant;

@Service
@Transactional
public class SocialIdentityService {

	//Managed repository
	@Autowired
	private SocialIdentityRepository socialRepository;

	
	//Supporting services
	@Autowired
	private LessorService lessorService;
	@Autowired
	private TenantService tenantService;
	@Autowired
	private AuditorService auditorService;
	@Autowired
	private AdministratorService adminService;
	@Autowired
	private Validator validator;
	
	//Constructors
	public SocialIdentityService() {
		super();
	}
	
	//Simple CRUD methods
	public SocialIdentity create(Actor actor) {
		Assert.notNull(actor, "Thet actor cannot be null");
		SocialIdentity res= new SocialIdentity();
		res.setActor(actor);
		return res;
	}
	
	
	public Collection<SocialIdentity> findAll() {
		Collection<SocialIdentity> res = socialRepository.findAll();
		return res;
	}

	public SocialIdentity findOne(int socialId) {
		SocialIdentity res = socialRepository.findOne(socialId);
		return res;
	}
	

	public SocialIdentity save(SocialIdentity socialIdentity) {
		Assert.notNull(socialIdentity, "The social identity to save cannot be null.");
		SocialIdentity res = socialRepository.save(socialIdentity);
		res.getActor().getSocialIdentities().add(socialIdentity);
		return res;
	}
	
	
	public SocialIdentity reconstruct(SocialIdentity socialIdentity, BindingResult binding) {
		SocialIdentity res = new SocialIdentity();
		
		//Tenant
		Authority t = new Authority();
		t.setAuthority(Authority.TENANT);
		//Lessor
		Authority l = new Authority();
		l.setAuthority(Authority.LESSOR);
		//Auditor
		Authority a = new Authority();
		a.setAuthority(Authority.AUDITOR);
		//Admin
		Authority admin = new Authority();
		admin.setAuthority(Authority.ADMIN);
		
		UserAccount ua= LoginService.getPrincipal();
		if(socialIdentity.getId()==0){
			if(ua.getAuthorities().contains(t)){
				Tenant tenant= tenantService.findByUserAccountId(LoginService.getPrincipal().getId());
				res = this.create(tenant);
			}else if(ua.getAuthorities().contains(l)){
				Lessor lessor= lessorService.findByUserAccountId(LoginService.getPrincipal().getId());
				res = this.create(lessor);
			}else if(ua.getAuthorities().contains(a)){
				Auditor auditor= auditorService.findByUserAccountId(LoginService.getPrincipal().getId());
				res = this.create(auditor);
			}else if(ua.getAuthorities().contains(admin)){
				Administrator adminis= adminService.findByUserAccountId(LoginService.getPrincipal().getId());
				res = this.create(adminis);
			}
			
		}else{
			res = socialRepository.findOne(socialIdentity.getId());
		}
		res.setNick(socialIdentity.getNick());
		res.setNameSocial(socialIdentity.getNameSocial());
		res.setUrlSocial(socialIdentity.getUrlSocial());
		validator.validate(res, binding);
		return res;
		}
	
	
	
	public Collection<SocialIdentity> findMySocialIdentities(){
		UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		Authority a = new Authority();
		a.setAuthority(Authority.TRIAL);
		Assert.isTrue(!ua.getAuthorities().contains(a), "You must to be authenticate to find your folders.");
		return socialRepository.findMySocialIdentities(ua.getId());
	}
	
	public void delete(SocialIdentity socialIdentity) {
		
		Authority b = new Authority();
		b.setAuthority(Authority.ADMIN);
		
		UserAccount ua=LoginService.getPrincipal();
		Assert.isTrue(socialIdentity.getActor().getUserAccount().equals(ua) || ua.getAuthorities().contains(b), "Your note the owner or this social identity");
		Assert.notNull(socialIdentity, "The social identity to delete cannot be null.");
		assert socialIdentity.getId() != 0;
		Assert.isTrue(socialRepository.exists(socialIdentity.getId()));
		socialRepository.delete(socialIdentity);
	}
}

