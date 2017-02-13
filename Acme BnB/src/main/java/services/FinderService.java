
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Finder;
import domain.Tenant;

@Service
@Transactional
public class FinderService {

	//Managed repository
	@Autowired
	private FinderRepository finderRepository;


	//Supporting services

	//Constructors
	public FinderService() {
		super();
	}

	//Simple CRUD methods
	public Finder create(Tenant tenant) {
		Assert.notNull(tenant);
		Finder res;
		res = new Finder();
		res.setTenant(tenant);

		return res;
	}

	public Collection<Finder> findAll() {
		Collection<Finder> res = finderRepository.findAll();
		return res;
	}

	public Finder findOne(int finderId) {
		Finder res = finderRepository.findOne(finderId);
		return res;
	}

	public Finder save(Finder finder) {
		Assert.notNull(finder, "The finder to save cannot be null.");
		Finder res = finderRepository.save(finder);
		res.getTenant().setFinder(res);
		return res;
	}

	public void delete(Finder finder) {
		Authority b = new Authority();
		b.setAuthority(Authority.ADMIN);

		UserAccount ua = LoginService.getPrincipal();
		Assert.isTrue(finder.getTenant().getUserAccount().equals(ua) || ua.getAuthorities().contains(b), "You are not the owner of this finder");
		Assert.notNull(finder, "The finder to delete cannot be null.");
		Assert.isTrue(finderRepository.exists(finder.getId()));

		finderRepository.delete(finder);
	}

	//Utilites methods
	public Finder findMyFinder(){
		Authority b = new Authority();
		b.setAuthority(Authority.TENANT);
		UserAccount ua=LoginService.getPrincipal();
		
		Assert.isTrue(ua.getAuthorities().contains(b), "You must to be a tenant for this action");
		return finderRepository.findMyFinder(ua.getId());
	}

}
