
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select a from Administrator a where a.userAccount.id = ?1")
	Administrator findByUserAccountId(int id);
	
	//@Query("select avg(p.states.size) from Property p join p.states s where s.status!=domain.Status.PENDING group  by p.lessor")
	//select count(s) from State s where s.status=domain.Status.ACCEPTED group by s.property.lessor;
	
	//select (select count(*) from State s where s.status=domain.Status.ACCEPTED) / count(*)*1.0 from Lessor l;
	//select (select count(*) from State s where s.status=domain.Status.DENIED) / count(*)*1.0 from Lessor l;

	//select p.lessor from Property p where (p.states.size = (select count(s) from State s where s.status=domain.Status.ACCEPTED));

	@Query("select (select count(*) from Request r where r.status=domain.Status.ACCEPTED) / count(*)*1.0 from Lessor l")
	Double requestAcceptedByLessor();
	
	@Query("select (select count(*) from Request r where r.status=domain.Status.DENIED) / count(*)*1.0 from Lessor l")
	Double requestDeniedByLessor();
	
	@Query("select (select count(*) from Request r where r.status=domain.Status.ACCEPTED) / count(*)*1.0 from Tenant t")
	Double requestAcceptedByTenant();
	
	@Query("select (select count(*) from Request r where r.status=domain.Status.DENIED) / count(*)*1.0 from Tenant t")
	Double requestDeniedByTenant();
	
	//Hay que coger el primero de los que devuelve
	/*@Query("select p.lessor from Property p join p.requests r where r.status.size <=(select count(*) from Request r where r.status=domain.Status.ACCEPTED)")
	Lessor lessorMoreAccepted();
	@Query("select p.lessor from Property p join p.requests r where r.status.size <=(select count(*) from Request r where r.status=domain.Status.DENIED)")
	Lessor lessorMoreDenied();
	@Query("select p.lessor from Property p join p.requests r where r.status.size <=(select count(*) from Request r where r.status=domain.Status.PENDING)")
	Lessor lessorMorePending();
*/
	
	//El mas repetido de lo que devuelva
	/*@Query("select r.tenant from Request r where r.status = domain.Status.ACCEPTED")
	Tenant tennatMoreAccepted();
	@Query("select r.tenant from Request r where r.status = domain.Status.DENIED")
	Tenant tennatMoreDenied();
	@Query("select r.tenant from Request r where r.status = domain.Status.PENDING")
	Tenant tennatMorePending(); */
	
	
	//Level A
	
	@Query("select min(a.socialIdentities.size) from Actor a")
	Integer minSocialIdentityPerActor();
	@Query("select max(a.socialIdentities.size) from Actor a")
	Integer maxSocialIdentityPerActor();
	@Query("select avg(a.socialIdentities.size) from Actor a")
	Integer avgSocialIdentityPerActor();
	
/*	The minimum, the average, and the maximum number of invoices issued to
	the tenants.
	@Query("")
	Integer minInvoicePerTenant();
	@Query("")
	Integer maxInvoicePerTenant();
	@Query("")
	Integer avgInvoicePerTenant(); */
	
	@Query("select sum(i.totalAmount) from Invoice i")
	Double totalAmountMoney();
	
	//Hay que unir estas dos querys pero no se si con un dividir
	//select avg(p) from Property p where p.audits.size>0;
	//select avg(p) from Property p where p.audits.size=0;
	
	
}
