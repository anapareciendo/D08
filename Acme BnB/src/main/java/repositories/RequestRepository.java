
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

	@Query("select r from Request r where r.tenant.userAccount.id = ?1")
	Collection<Request> findMyRequest(int id);
	
	@Query("select r from Request r where r.property.lessor.userAccount.id=?1 and (r.status=domain.Status.DENIED or r.status=domain.Status.PENDING)")
	Collection<Request> findMyRequestDeniedProperties(int id);
	
	@Query("select r from Request r where r.property.lessor.userAccount.id=?1 and (r.status=domain.Status.ACCEPTED or r.status=domain.Status.PENDING)")
	Collection<Request> findMyRequestAcceptProperties(int id);
	
}
