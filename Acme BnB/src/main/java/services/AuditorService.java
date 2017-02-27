
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AuditorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Auditor;
import domain.Comment;
import domain.SocialIdentity;

@Service
@Transactional
public class AuditorService {

	//Managed repository
	@Autowired
	private AuditorRepository auditorRepository;


	//Supporting services

	//Constructors
	public AuditorService() {
		super();
	}

	//Simple CRUD methods
	public Auditor create() {
		Auditor res;
		res = new Auditor();
		res.setPostComments(new ArrayList<Comment>());
		res.setSocialIdentities(new ArrayList<SocialIdentity>());
		res.setComments(new ArrayList<Comment>());
		
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

}
