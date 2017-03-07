
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.Property;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select f from Finder f where f.tenant.userAccount.id = ?1")
	Finder findMyFinder(int id);
	
	@Query("select p from Property p where p.ratePerDay>?1 and p.ratePerDay<?2")
	Collection<Property> searchFinder(Double minPrice, Double maxPrice);
	
	@Query("select p from Property p where p.address like '%'||:city||'%' and p.address like '%'||:address||'%' and p.name like '%'||:keyword||'%'")
	Collection<Property> searchFinder(@Param("city") String city, @Param("address") String address, @Param("keyword") String keyword);

}
