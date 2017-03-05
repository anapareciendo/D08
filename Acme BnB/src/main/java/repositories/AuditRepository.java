
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {

//	@Query("select p.audits from Property p where p.lessor.userAccount.id = ?1")
//	Collection<Audit> findMyAudits(int id);
	
	@Query("select a from Audit a where a.auditor.userAccount.id=?1 and a.draft=true")
	Collection<Audit> findMyDraftAudits(int auditorId);
	
	@Query("select a from Audit a where a.draft=false and a.property.id=?1")
	Collection<Audit> findAllNoDraft(int propertyId);
}
