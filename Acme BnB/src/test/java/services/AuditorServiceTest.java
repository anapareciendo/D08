package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Auditor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class AuditorServiceTest extends AbstractTest{
	
	@Autowired
	private AuditorService auditorService;
	

	
	@Test 
	public void testCreate(){
		Auditor a= auditorService.create();
		Assert.isNull(a.getName());
		Assert.isNull(a.getSurname());
		Assert.isNull(a.getEmail());
	}
//	@Test
//	public void testSave(){
//		Administrator a= administratorService.create();
//		Assert.notNull(a);
//		UserAccount uc = new UserAccount();
//		uc.setUsername("userTest");
//		uc.setPassword("212323497a57a5v");
//		
//		Authority au = new Authority();
//		au.setAuthority("ADMIN");
//		Collection<Authority> aus = new ArrayList<Authority>();
//		aus.add(au);
//		uc.setAuthorities(aus);	
//		
//		a.setUserAccount(uc);
//		a.setName("Carmen");
//		a.setSurname("Surname");
//		a.setEmail("rosalmalvar@gmail.com");
//	
//		Administrator a2= administratorService.save(a);
//		Assert.isTrue(administratorService.findAll().contains(a2));
//	}
//	
//	@Test
//	public void testDelete(){
//		Administrator a= new Administrator();
//		Assert.notNull(a);
//		UserAccount uc = new UserAccount();
//		uc.setUsername("adminTest");
//		uc.setPassword("212323497a57a5v");
//		
//		Authority au = new Authority();
//		au.setAuthority("ADMIN");
//		Collection<Authority> aus = new ArrayList<Authority>();
//		aus.add(au);
//		uc.setAuthorities(aus);	
//		
//		a.setUserAccount(uc);
//		a.setName("Carmen");
//		a.setSurname("Surname");
//		a.setEmail("rosalmalvar@gmail.com");
//		a.setFolders(new ArrayList<Folder>());
//		a.setClasses(new ArrayList<MasterClass>());
//		
//		Administrator a2= administratorService.save(a);
//		
//		super.authenticate("adminTest");
//		
//		a2.setFolders(new ArrayList<Folder>());
//		administratorService.delete(a2);
//		Assert.isTrue(!administratorService.findAll().contains(a2));
//		
//		super.unauthenticate();
//	}
	
	

}
