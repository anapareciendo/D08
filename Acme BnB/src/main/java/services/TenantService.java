
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TenantRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Finder;
import domain.Invoice;
import domain.Request;
import domain.Tenant;

@Service
@Transactional
public class TenantService {

	//Managed repository
	@Autowired
	private TenantRepository	tenantRepository;


	//Supporting services

	//Constructors
	public TenantService() {
		super();
	}

	//Simple CRUD methods
	public Tenant create() {
		Tenant res;
		res = new Tenant();
		res.setFinders(new ArrayList<Finder>());
		res.setRequests(new ArrayList<Request>());
		res.setInvoices(new ArrayList<Invoice>());

		return res;
	}

	public Collection<Tenant> findAll() {
		Collection<Tenant> res = tenantRepository.findAll();
		return res;
	}

	public Tenant findOne(int tenantId) {
		Tenant res = tenantRepository.findOne(tenantId);
		return res;
	}

	public Tenant save(Tenant tenant) {
		Assert.notNull(tenant, "The tenant to save cannot be null.");
		Tenant res = tenantRepository.save(tenant);

		return res;
	}

	public void delete(Tenant tenant) {
		UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(ua.getAuthorities().contains(a), "You must to be a admin to delete an actor.");

		Assert.notNull(tenant, "The tenant to delete cannot be null.");
		Assert.isTrue(tenantRepository.exists(tenant.getId()));

		Assert.isTrue(tenant.getFinders().isEmpty(), "The tenant cannot be delete with finders");
		Assert.isTrue(tenant.getRequests().isEmpty(), "The tenant cannot be delete with requests");
		Assert.isTrue(tenant.getInvoices().isEmpty(), "The tenant cannot be delete with campaigns");

		tenantRepository.delete(tenant);
	}

	//Utilites methods

}
