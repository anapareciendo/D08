package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.LessorRepository;
import repositories.SocialIdentityRepository;
import security.LoginService;
import domain.Actor;
import domain.Lessor;
import domain.Property;
import domain.SocialIdentity;

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
	private Validator validator;
	
	//Constructors
	public SocialIdentityService() {
		super();
	}
	
	//Simple CRUD methods
	public SocialIdentity create(Actor actor) {
		SocialIdentity res;
		res = new SocialIdentity();
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
	
	public SocialIdentity save(SocialIdentity social) {
		Assert.notNull(social, "The social identity to save cannot be null.");
		SocialIdentity res = socialRepository.save(social);
		return res;
	}
	
	
	
	//Utilites methods
	
	public SocialIdentity reconstruct(SocialIdentity socialIdentity, BindingResult binding) {
		SocialIdentity res;
		
		if(socialIdentity.getId()==0){
			Lessor lessor= lessorService.findByUserAccountId(LoginService.getPrincipal().getId());
			res = this.create(lessor);
		}else{
			res = socialRepository.findOne(socialIdentity.getId());
			res.setNick(socialIdentity.getNick());
			res.setNameSocial(socialIdentity.getNameSocial());
			res.setUrlSocial(socialIdentity.getUrlSocial());
			
		}

		validator.validate(res, binding);
		return res;
	}
	
}

