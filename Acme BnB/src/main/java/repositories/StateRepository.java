
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.State;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {

}
