
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
public class AdministratorService {

	//Managed repository
	@Autowired
	private AdministratorRepository administratorRepository;


	//Supporting services

	//Constructors
	public AdministratorService() {
		super();
	}

	//Simple CRUD methods
	public Administrator create() {
		Administrator res;
		res = new Administrator();
		res.setPostComments(new ArrayList<Comment>());
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
		Assert.isTrue(admin.getSocialIdentities().isEmpty(), "The administrator cannot be delete with social identities");

		administratorRepository.delete(admin);
	}

	//Utilites methods
	public Administrator findByUserAccountId(int id){
		Assert.notNull(id);
		return administratorRepository.findByUserAccountId(id);
	}

	
	private void isAdministrator(){
		UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(ua.getAuthorities().contains(a), "You must to be a administrator for this action.");
	}
	
	public Double requestAcceptedByLessor(){
		this.isAdministrator();
		return administratorRepository.requestAcceptedByLessor();
	}
	
	public Double requestDeniedByLessor(){
		this.isAdministrator();
		return administratorRepository.requestDeniedByLessor();
	}
	
	public Double requestAcceptedByTenant(){
		this.isAdministrator();
		return administratorRepository.requestAcceptedByTenant();
	}
	
	public Double requestDeniedByTenant(){
		this.isAdministrator();
		return administratorRepository.requestDeniedByTenant();
	}
	
	/*public Lessor lessorMoreAccepted(){
		this.isAdministrator();
		return administratorRepository.lessorMoreAccepted();
	}
	
	public Lessor lessorMoreDenied(){
		this.isAdministrator();
		return administratorRepository.lessorMoreDenied();
	}
	
	public Lessor lessorMorePending(){
		this.isAdministrator();
		return administratorRepository.lessorMorePending();
	}
	
	public Tenant tennatMoreAccepted(){
		this.isAdministrator();
		return administratorRepository.tennatMoreAccepted();
	}
	
	public Tenant tennatMoreDenied(){
		this.isAdministrator();
		return administratorRepository.tennatMoreDenied();
	}
	
	public Tenant tennatMorePending(){
		this.isAdministrator();
		return administratorRepository.tennatMorePending();
	}
	*/
	public Double minSocialIdentityPerActor(){
		this.isAdministrator();
		return administratorRepository.minSocialIdentityPerActor();
	}
	
	
	public Double maxSocialIdentityPerActor(){
		this.isAdministrator();
		return administratorRepository.maxSocialIdentityPerActor();
	}
	
	public Double avgSocialIdentityPerActor(){
		this.isAdministrator();
		return administratorRepository.avgSocialIdentityPerActor();
	}
	
	/*
	public Integer minInvoicePerActor(){
		this.isAdministrator();
		return administratorRepository.minInvoicePerActor();
	}
	
	public Integer maxInvoicePerActor(){
		this.isAdministrator();
		return administratorRepository.maxInvoicePerActor();
	}
	
	public Integer avgInvoicePerActor(){
		this.isAdministrator();
		return administratorRepository.avgInvoicePerActor();
	}
	 */
	
	public Double totalAmountMoney(){
		this.isAdministrator();
		return administratorRepository.totalAmountMoney();
	}
	
	
	
}
