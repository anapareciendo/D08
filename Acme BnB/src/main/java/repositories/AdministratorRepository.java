
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Lessor;
import domain.Property;
import domain.Tenant;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select a from Administrator a where a.userAccount.id = ?1")
	Administrator findByUserAccountId(int id);
	
	//The lessors who have aprroved more requests (Level C)
	@Query("select distinct(p.lessor) from Property p join p.requests r where p.requests.size = (select max(p.requests.size) from Property p join p.requests r where r.status = 2) AND r.status = 2")
	Collection <Lessor> lessorMaxRequestsAccepted();
	
	//The lessors who have denied more requests (Level C)
	@Query("select distinct(p.lessor) from Property p join p.requests r where p.requests.size = (select max(p.requests.size) from Property p join p.requests r where r.status = 3) AND r.status = 3")
	Collection <Lessor> lessorMaxRequestsDenied();
	
	//The lessors who have more pending requests (Level C)
	@Query("select distinct(p.lessor) from Property p join p.requests r where p.requests.size = (select max(p.requests.size) from Property p join p.requests r where r.status = 1) AND r.status = 1")
	Collection <Lessor> lessorMaxRequestsPending();
	
	//The tenants who have got more requests approved (Level C)
	@Query("select distinct(t) from Tenant t join t.requests r where t.requests.size = (select max(t.requests.size) from Tenant t join t.requests r where r.status = 2) AND r.status = 2")
	Collection<Tenant> tenantMaxRequestsAccepted();
	
	//The tenants who have got more requests denied (Level C)
	@Query("select distinct(t) from Tenant t join t.requests r where t.requests.size = (select max(t.requests.size) from Tenant t join t.requests r where r.status = 3) AND r.status = 3")
	Collection<Tenant> tenantMaxRequestsDenied();
	
	//The tenants who have more pending requests (Level C)
	@Query("select distinct(t) from Tenant t join t.requests r where t.requests.size = (select max(t.requests.size) from Tenant t join t.requests r where r.status = 1) AND r.status = 1")
	Collection<Tenant> tenantMaxRequestsPending();
	
	
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
	
	//Level B
	
	//The minimum, the average, and the maximum number of audits that the properties have got
	@Query("select min(a.audits.size) from Auditor a")
	Double minAuditsHaveProperty();
	@Query("select max(a.audits.size) from Auditor a")
	Double maxAuditsHaveProperty();
	@Query("select avg(a.audits.size) from Auditor a")
	Double avgAuditsHaveProperty();
	
	//A listing in which the attributes are sorted in descending order regarding the number of times they have been used to describe a property
	
	//A listing with his or her properties sorted according to the number of audits that they have got
	@Query("select p from Property p order by p.audits.size DESC")
	Collection<Property> propertiesAccordingAudits();
	
	//A listing with his or her properties sorted according to the number of requests that they have got
	@Query("select p from Property p order by p.requests.size DESC")
	Collection<Property> propertiesAccordingRequest();
	
	//A listing with his or her properties sorted according to the number of approved requests that they have got.
	
	
	//Level A
	
	@Query("select min(a.socialIdentities.size) from Actor a")
	Double minSocialIdentityPerActor();
	@Query("select max(a.socialIdentities.size) from Actor a")
	Double maxSocialIdentityPerActor();
	@Query("select (select count(*) from SocialIdentity s) / count(*)*1.0 from Actor a")
	Double avgSocialIdentityPerActor();
	
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
