
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Lessor;

@Repository
public interface LessorRepository extends JpaRepository<Lessor, Integer> {

	@Query("select l from Lessor l where l.userAccount.id = ?1")
	Lessor findByUserAccountId(int id);
	
	//Dashboard
	//select p from Property p order by p.audits
	//select p from Property p order by p.requests
	//select p from Property p order by p.requests where
	
	@Query("select distinct l from Lessor l join l.properties p join p.requests r where r.tenant.id = ?1")
	Collection<Lessor> findMyLessors(int id);
	

}
