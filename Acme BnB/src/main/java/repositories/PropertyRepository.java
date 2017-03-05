
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {
	
	@Query("select p from Property p where p.lessor.userAccount.id = ?1")
	Collection<Property> findMyProperties(int id);
	
	@Query("select p from Property p order by p.requests.size desc")
	Collection<Property> findAllSortedRequest();

}
