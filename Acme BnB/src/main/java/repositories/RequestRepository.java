
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Finder;

@Repository
public interface RequestRepository extends JpaRepository<Finder, Integer> {

}
