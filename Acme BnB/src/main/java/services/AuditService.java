
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AuditRepository;
import domain.Audit;
import domain.Auditor;

@Service
@Transactional
public class AuditService {

	//Managed repository
	@Autowired
	private AuditRepository auditRepository;


	//Supporting services

	//Constructors
	public AuditService() {
		super();
	}

	//Simple CRUD methods
	public Audit create(Auditor auditor) {
		Assert.notNull(auditor);
		Audit res;
		res = new Audit();
		res.setAuditor(auditor);
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
		res.getAuditor().getAudits().add(res);
		return res;
	}



}
