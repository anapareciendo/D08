
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Tenant;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Integer> {

	@Query("select t from Tenant t where t.userAccount.id = ?1")
	Tenant findByUserAccountId(int id);
	
	@Query("select distinct t from Tenant t join t.requests r join r.property p where p.lessor.id = ?1")
	Collection<Tenant> findMyTenants(int id);
}
