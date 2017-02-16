package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SocialIdentityRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Attribute;
import domain.Property;
import domain.SocialIdentity;

@Service
@Transactional
public class SocialIdentityService {

	//Managed repository
	@Autowired
	private SocialIdentityRepository socialRepository;
	
	//Supporting services
	
	//Constructors
	public SocialIdentityService() {
		super();
	}
	
	//Simple CRUD methods
	public SocialIdentity create() {
		SocialIdentity res;
		res = new SocialIdentity();
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
	
}

