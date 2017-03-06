package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FeeRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Fee;

@Service
@Transactional
public class FeeService {

	//Managed repository
	@Autowired
	private FeeRepository feeRepository;
	
	//Supporting services
	
	//Constructors
	public FeeService() {
		super();
	}
	
	//Simple CRUD methods
	public Fee create() {
		Fee res;
		res = new Fee();
		res.setCost(1.0);
		res.setMoment(Calendar.getInstance().getTime());
		
		return res;
	}
	
	public Collection<Fee> findAll() {
		Collection<Fee> res = feeRepository.findAll();
		return res;
	}

	public Fee findOne(int feeId) {
		Fee res = feeRepository.findOne(feeId);
		return res;
	}
	
	public Fee save(Fee fee) {
		Assert.notNull(fee, "The fee to save cannot be null.");
		Authority b = new Authority();
		b.setAuthority(Authority.ADMIN);

		UserAccount ua=LoginService.getPrincipal();
		Assert.isTrue(ua.getAuthorities().contains(b), "You must to be an admin for this acction");
		Fee res = feeRepository.save(fee);
		return res;
	}
	
	public void delete(Fee fee) {
		Assert.notNull(fee, "The value to delete cannot be null.");
		Assert.isTrue(feeRepository.exists(fee.getId()));
		
		Authority b = new Authority();
		b.setAuthority(Authority.ADMIN);

		UserAccount ua=LoginService.getPrincipal();
		Assert.isTrue(ua.getAuthorities().contains(b), "You must to be an admin for this acction");
		
		feeRepository.delete(fee);
	}
	
	
	//Utilites methods
}

