
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Comment;
import domain.SocialIdentity;

@Service
@Transactional
public class LessorService {

	//Managed repository
	@Autowired
	private AdministratorRepository administratorRepository;


	//Supporting services

	//Constructors
	public LessorService() {
		super();
	}

	//Simple CRUD methods
	public Administrator create() {
		Administrator res;
		res = new Administrator();
		res.setPostComments(new ArrayList<Comment>());
		res.setReciveComments(new ArrayList<Comment>());
		res.setSocialIdentities(new ArrayList<SocialIdentity>());

		return res;
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> res = administratorRepository.findAll();
		return res;
	}

	public Administrator findOne(int adminId) {
		Administrator res = administratorRepository.findOne(adminId);
		return res;
	}

	public Administrator save(Administrator admin) {
		Assert.notNull(admin, "The tenant to save cannot be null.");
		Administrator res = administratorRepository.save(admin);

		return res;
	}

	public void delete(Administrator admin) {
		UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(ua.getAuthorities().contains(a), "You must to be a admin to delete an actor.");

		Assert.notNull(admin, "The adminstrator to delete cannot be null.");
		Assert.isTrue(administratorRepository.exists(admin.getId()));

		Assert.isNull(admin.getPostComments().isEmpty(), "The administrator cannot be delete with post comments");
		Assert.isTrue(admin.getReciveComments().isEmpty(), "The administrator cannot be delete with recive comments");
		Assert.isTrue(admin.getSocialIdentities().isEmpty(), "The administrator cannot be delete with social identities");

		administratorRepository.delete(admin);
	}

	//Utilites methods
	public Administrator findByUserAccountId(int id){
		Assert.notNull(id);
		return administratorRepository.findByUserAccountId(id);
	}

}
