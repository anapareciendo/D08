
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FinderRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Finder;
import domain.Property;
import domain.Tenant;

@Service
@Transactional
public class FinderService {

	//Managed repository
	@Autowired
	private FinderRepository finderRepository;


	//Supporting services
	@Autowired
	private TenantService tenantService;
	
	@Autowired
	private Validator validator;

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
		res.setMoment(Calendar.getInstance().getTime());

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

	public Finder reconstruct(Finder finder, BindingResult binding) {
		Finder res;
		if(finder.getId()==0){
			res=this.create(tenantService.findByUserAccountId(LoginService.getPrincipal().getId()));
		}else{
			res=finderRepository.findOne(finder.getId());
		}
		res.setDestinationCity(finder.getDestinationCity());
		res.setMaxPrice(finder.getMaxPrice());
		res.setMinPrice(finder.getMinPrice());
		res.setKeyword(finder.getKeyword());
		res.setAddress(finder.getAddress());
		res.setDescription(finder.getDescription());
		validator.validate(res, binding);
		return res;
	}
	
	public Collection<Property> searchFincer(String address, Double minPrice, Double maxPrice, String city, String keyword){
		Double mp = minPrice;
		Double xp = maxPrice;
		if(mp==null){
			mp=0.0;
		}
		if(xp==null){
			xp=Double.MAX_VALUE;
		}
		Set<Property> res=new HashSet<Property>(finderRepository.searchFinder(address, mp, xp));
		res.addAll(finderRepository.searchFinder(city,keyword));
 		return res;
	}

	public boolean isActive() {
		boolean res = true;
		Finder finder = tenantService.findByUserAccountId(LoginService.getPrincipal().getId()).getFinder();
		if(finder==null){
			res=false;
		}else{
			long actual = Calendar.getInstance().getTime().getTime();
			long old = finder.getMoment().getTime();
			if(actual-old > 3600000){
				res=false;
			}
		}
		return res;
	}

}
