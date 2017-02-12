
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LessorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Comment;
import domain.Lessor;
import domain.Property;
import domain.SocialIdentity;

@Service
@Transactional
public class LessorService {

	//Managed repository
	@Autowired
	private LessorRepository lessorRepository;


	//Supporting services

	//Constructors
	public LessorService() {
		super();
	}

	//Simple CRUD methods
	public Lessor create() {
		Lessor res;
		res = new Lessor();
		res.setPostComments(new ArrayList<Comment>());
		res.setReciveComments(new ArrayList<Comment>());
		res.setSocialIdentities(new ArrayList<SocialIdentity>());
		res.setProperties(new ArrayList<Property>());
		
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
		Assert.isTrue(lessor.getReciveComments().isEmpty(), "The lessor cannot be delete with recive comments");
		Assert.isTrue(lessor.getSocialIdentities().isEmpty(), "The lessor cannot be delete with social identities");
		Assert.isTrue(lessor.getProperties().isEmpty(), "The lessor cannot be delete with properties");

		lessorRepository.delete(lessor);
	}

	//Utilites methods
	public Lessor findByUserAccountId(int id){
		Assert.notNull(id);
		return lessorRepository.findByUserAccountId(id);
	}

}
