package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

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
	
	@Autowired
	private Validator validator;
	
	//Constructors
	public FeeService() {
		super();
	}
	
	//Simple CRUD methods
	public Fee create() {
		Fee res;
		res = new Fee();
		res.setCost(1.0);
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
	public Fee getFee(){
		return feeRepository.getFee();
	}

	public Fee reconstruc(Fee fee, BindingResult binding) {
		Fee res = feeRepository.findOne(fee.getId());
		res.setCost(fee.getCost());
		validator.validate(res, binding);
		
		return res;
	}
}

