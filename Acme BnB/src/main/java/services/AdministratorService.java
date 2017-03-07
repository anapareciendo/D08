
package services;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Attribute;
import domain.Comment;
import domain.Lessor;
import domain.Property;
import domain.SocialIdentity;
import domain.Tenant;

@Service
@Transactional
public class AdministratorService {

	//Managed repository
	@Autowired
	private AdministratorRepository administratorRepository;

	//Validator
	@Autowired
	private Validator validator;


	//Supporting services

	//Constructors
	public AdministratorService() {
		super();
	}

	//Simple CRUD methods
	public Administrator create(UserAccount ua) {
		Administrator res;
		res = new Administrator();
		res.setPostComments(new ArrayList<Comment>());
		res.setSocialIdentities(new ArrayList<SocialIdentity>());
		res.setUserAccount(ua);
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
	
	//Ana
	public Collection <Lessor> lessorMaxRequestsAccepted(){
		this.isAdministrator();
		return administratorRepository.lessorMaxRequestsAccepted();
	}
	
	public Collection <Lessor> lessorMaxRequestsDenied(){
		this.isAdministrator();
		return administratorRepository.lessorMaxRequestsDenied();
	}
	
	public Collection <Lessor> lessorMaxRequestsPending(){
		this.isAdministrator();
		return administratorRepository.lessorMaxRequestsPending();
	}
	
	public Collection<Tenant> tenantMaxRequestsAccepted(){
		this.isAdministrator();
		return administratorRepository.tenantMaxRequestsAccepted();
	}
	
	public Collection<Tenant> tenantMaxRequestsDenied(){
		this.isAdministrator();
		return administratorRepository.tenantMaxRequestsDenied();
	}
	
	public Collection<Tenant> tenantMaxRequestsPending(){
		this.isAdministrator();
		return administratorRepository.tenantMaxRequestsPending();
	}
	
	public Collection<Attribute> attributesDescTimesUsed(){
		this.isAdministrator();
		return administratorRepository.attributesDescTimesUsed();
	}
	
	public Collection<Property> propertiesOrderByAudits(){
		this.isAdministrator();
		return administratorRepository.propertiesOrderByAudits();
	}
	
	public Collection<Property> propertiesOrderByRequestsAccepted(){
		this.isAdministrator();
		return administratorRepository.propertiesOrderByRequestsAccepted();
	}
	
	public Collection<Property> propertiesOrderByRequestsDenied(){
		this.isAdministrator();
		return administratorRepository.propertiesOrderByRequestsDenied();
	}
	
	public Collection<Property> propertiesOrderByRequestsPending(){
		this.isAdministrator();
		return administratorRepository.propertiesOrderByRequestsPending();
	}
	
	public Double maxInvoicesPerTenant(){
		this.isAdministrator();
		Collection<Double> numInvoices = administratorRepository.numInvoicesPerTenant();
		return Collections.max(numInvoices);
	}
	
	public Double minInvoicesPerTenant(){
		this.isAdministrator();
		Collection<Double> numInvoices = administratorRepository.numInvoicesPerTenant();
		return Collections.min(numInvoices);
	}
	
	public Double avgInvoicesPerTenant(){
		this.isAdministrator();
		Double totalInvoices = administratorRepository.totalInvoices();
		Collection<Double> numInvoices = administratorRepository.numInvoicesPerTenant();
		Double suma = 0.0;
		for(Double i : numInvoices)
			suma = suma + i;
		
		return (suma/totalInvoices);
	}
	
	//Carmen
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
	
	public Double avgRequestsPropertyNoAudits(){
		this.isAdministrator();
		return administratorRepository.avgRequestsPropertyNoAudits();
	}
	public Double avgRequestsPropertyAudits(){
		this.isAdministrator();
		return administratorRepository.avgRequestsPropertyAudits();
	}
	
	public String ratioLessors(){
		this.isAdministrator();
		Double res;
		res = administratorRepository.totalLessorAccepted() / administratorRepository.totalLessors();
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(res);
	}
	
	public String ratioTenants(){
		this.isAdministrator();
		Double res;
		res = administratorRepository.totalTenantAccepted() / administratorRepository.totalTenants();
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(res);
	}
	
	
	public Administrator reconstruct(Administrator admin, BindingResult binding) {
		Administrator res;
		
		if(admin.getId()==0){
			res = this.create(LoginService.getPrincipal());
		}else{
			res = administratorRepository.findOne(admin.getId());
		}
		res.setName(admin.getName());
		res.setSurname(admin.getSurname());
		res.setEmail(admin.getEmail());
		res.setPhone(admin.getPhone());
		res.setPicture(admin.getPicture());
		validator.validate(res, binding);
		return res;
	}
	
	
}
