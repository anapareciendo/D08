
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
	
	@Query("select p from Property p where p.address=?1 and p.ratePerDay>?2 and p.ratePerDay<?3")
	Collection<Property> searchFinder(String address, Double minPrice, Double maxPrice);
	
	@Query("select p from Property p where p.address like '%'||:city||'%' and p.name like '%'||:keyword||'%'")
	Collection<Property> searchFinder(@Param("city") String city, @Param("keyword") String keyword);

}
