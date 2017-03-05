
package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AuditRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Audit;
import domain.Auditor;
import domain.Property;

@Service
@Transactional
public class AuditService {

	//Managed repository
	@Autowired
	private AuditRepository auditRepository;
	
	//Supporting services
	@Autowired
	private AuditorService auditorService;
	@Autowired
	private PropertyService propertyService;
	
	@Autowired
	private Validator validator;

	//Constructors
	public AuditService() {
		super();
	}

	//Simple CRUD methods
	public Audit create(Auditor auditor, Property property) {
		Assert.notNull(auditor);
		Audit res;
		res = new Audit();
		res.setAuditor(auditor);
		res.setProperty(property);
		res.setMoment(Calendar.getInstance().getTime());
		res.setDraft(true);
		//Creo que hay que cambiarlo en el domain
	//	res.setProperty(new ArrayList<Property>());
	
		return res;
	}

	public Collection<Audit> findAll() {
		Collection<Audit> res = auditRepository.findAll();
		return res;
	}

	public Audit findOne(int auditId) {
		Audit res = auditRepository.findOne(auditId);
		return res;
	}

	public Audit save(Audit audit) {
		Assert.notNull(audit, "The audit to save cannot be null.");
		Audit res = auditRepository.save(audit);
		Assert.isTrue(propertyService.findNotAuditProperty().contains(audit.getProperty()),"You can make only one Audit per Property");
		res.getAuditor().getAudits().add(res);
		res.getProperty().getAudits().add(res);
		return res;
	}
	
	
	//-----Other Methods
//	public Collection<Audit> findMyAudits(){
//		Authority t = new Authority();
//		t.setAuthority(Authority.TENANT);
//		Authority l = new Authority();
//		l.setAuthority(Authority.LESSOR);
//		Authority a = new Authority();
//		a.setAuthority(Authority.AUDITOR);
//		Authority admin = new Authority();
//		admin.setAuthority(Authority.ADMIN);
//		UserAccount ua=LoginService.getPrincipal();
//		Assert.isTrue(ua.getAuthorities().contains(t) || 
//				ua.getAuthorities().contains(l) ||
//				ua.getAuthorities().contains(a) || 
//				ua.getAuthorities().contains(admin) , "You must to be authenticte for this action");
//		return auditRepository.findMyAudits(ua.getId());
//	}
	
	public Collection<Audit> findMyDraftAudits(int auditorId){
		Authority a = new Authority();
		a.setAuthority(Authority.AUDITOR);
		UserAccount ua=LoginService.getPrincipal();
		Assert.isTrue(ua.getAuthorities().contains(a),"You must to be an Auditor for this action");
		return auditRepository.findMyDraftAudits(ua.getId());
	}
	
	public Collection<Audit> findAllNoDraft(int propertyId){
		Authority t = new Authority();
		t.setAuthority(Authority.TENANT);
		Authority l = new Authority();
		l.setAuthority(Authority.LESSOR);
		Authority a = new Authority();
		a.setAuthority(Authority.AUDITOR);
		Authority admin = new Authority();
		admin.setAuthority(Authority.ADMIN);
		UserAccount ua=LoginService.getPrincipal();
		Assert.isTrue(ua.getAuthorities().contains(t) || 
				ua.getAuthorities().contains(l) ||
				ua.getAuthorities().contains(a) || 
				ua.getAuthorities().contains(admin) , "You must to be authenticte for this action");
		return auditRepository.findAllNoDraft(propertyId);
	}

	public Audit reconstruct(Audit audit, BindingResult binding) {
		Audit res=this.create(auditorService.findByUserAccountId(LoginService.getPrincipal().getId()),audit.getProperty());

		res.setAttachments(audit.getAttachments());
		res.setText(audit.getText());
		validator.validate(res, binding);
		return res;
	}
		

}
