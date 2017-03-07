/* CurriculumCustomerController.java
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers.admin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import controllers.AbstractController;
import domain.Attribute;
import domain.Lessor;
import domain.Property;
import domain.Tenant;

@Controller
@RequestMapping("/dashboard/admin")
public class DashboardAdminController extends AbstractController {

	@Autowired
	private AdministratorService adminService;
	
	public DashboardAdminController() {
		super();
	}
		
	
 	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		//Josema
		Double avgRequestsPropertyAudits = adminService.avgRequestsPropertyAudits();
		Double avgRequestsPropertyNoAudits = adminService.avgRequestsPropertyNoAudits();
		Double versusRequestsPropertyAudit = avgRequestsPropertyAudits/avgRequestsPropertyNoAudits;
		

		//Ana
		Collection<Lessor> lessorMaxRequestsDenied = adminService.lessorMaxRequestsDenied();
		Collection<Lessor> lessorMaxRequestsAccepted = adminService.lessorMaxRequestsAccepted();
		Collection<Lessor> lessorMaxRequestsPending = adminService.lessorMaxRequestsPending();
		Collection<Tenant> tenantMaxRequestsAccepted = adminService.tenantMaxRequestsAccepted();
		Collection<Tenant> tenantMaxRequestsDenied = adminService.tenantMaxRequestsDenied();
		Collection<Tenant> tenantMaxRequestsPending = adminService.tenantMaxRequestsPending();
		Collection<Attribute> attributesDescTimesUsed = adminService.attributesDescTimesUsed();
		Collection<Property> propertiesOrderByAudits = adminService.propertiesOrderByAudits(); 
		Collection<Property> propertiesOrderByRequestsAccepted = adminService.propertiesOrderByRequestsAccepted();
		Collection<Property> propertiesOrderByRequestsDenied = adminService.propertiesOrderByRequestsDenied();
		Collection<Property> propertiesOrderByRequestsPending = adminService.propertiesOrderByRequestsPending();
		Double maxInvoicesPerTenant = adminService.maxInvoicesPerTenant();
		Double minInvoicesPerTenant = adminService.minInvoicesPerTenant();
		Double avgInvoicesPerTenant  = adminService.avgInvoicesPerTenant();
		
		String ratioLessors = adminService.ratioLessors();
		String ratioTenants = adminService.ratioTenants();
		
		
		//Carmen
		Double requestAcceptedByLessor= adminService.requestAcceptedByLessor();
		Double requestDeniedByLessor= adminService.requestDeniedByLessor();
		
		Double requestAcceptedByTenant= adminService.requestAcceptedByTenant();
		Double requestDeniedByTenant = adminService.requestDeniedByTenant();
		
	/*	Lessor lessorMoreAccepted = adminService.lessorMoreAccepted();
		Lessor lessorMoreDenied = adminService.lessorMoreDenied();
		Lessor lessorMorePending = adminService.lessorMorePending();
		
		Tenant tennatMoreAccepted = adminService.tennatMoreAccepted();
		Tenant tennatMoreDenied = adminService.tennatMoreDenied();
		Tenant tennatMorePending = adminService.tennatMorePending();
		*/
		
		Double minSocialIdentityPerActor = adminService.minSocialIdentityPerActor();
		Double maxSocialIdentityPerActor = adminService.maxSocialIdentityPerActor();
		Double avgSocialIdentityPerActor = adminService.avgSocialIdentityPerActor();
		
		/*Integer minInvoicePerActor = adminService.minInvoicePerActor();
		Integer maxInvoicePerActor = adminService.maxInvoicePerActor();
		Integer avgInvoicePerActor = adminService.avgInvoicePerActor();
		*/
		
		Double totalAmountMoney = adminService.totalAmountMoney();
	
		result = new ModelAndView("administrator/dashboard");
		
		result.addObject("lessorMaxRequestsPending", lessorMaxRequestsPending);
		result.addObject("lessorMaxRequestsAccepted",lessorMaxRequestsAccepted);
		result.addObject("lessorMaxRequestsDenied",lessorMaxRequestsDenied);
		result.addObject("tenantMaxRequestsAccepted",tenantMaxRequestsAccepted);
		result.addObject("tenantMaxRequestsDenied",tenantMaxRequestsDenied);
		result.addObject("tenantMaxRequestsPending",tenantMaxRequestsPending);
		result.addObject("attributesDescTimesUsed", attributesDescTimesUsed);
		result.addObject("propertiesOrderByAudits", propertiesOrderByAudits);
		result.addObject("propertiesOrderByRequestsAccepted", propertiesOrderByRequestsAccepted);
		result.addObject("propertiesOrderByRequestsDenied", propertiesOrderByRequestsDenied);
		result.addObject("propertiesOrderByRequestsPending", propertiesOrderByRequestsPending);
		result.addObject("maxInvoicesPerTenant", maxInvoicesPerTenant);
		result.addObject("minInvoicesPerTenant", minInvoicesPerTenant);
		result.addObject("avgInvoicesPerTenant", avgInvoicesPerTenant);
		
		result.addObject("requestAcceptedByLessor", requestAcceptedByLessor);
		result.addObject("requestDeniedByLessor", requestDeniedByLessor);
		
		result.addObject("requestAcceptedByTenant", requestAcceptedByTenant);
		result.addObject("requestDeniedByTenant", requestDeniedByTenant);
		
	/*	result.addObject("lessorMoreAccepted", lessorMoreAccepted);
		result.addObject("lessorMoreDenied", lessorMoreDenied);
		result.addObject("lessorMorePending", lessorMorePending);
		
		result.addObject("tennatMoreAccepted", tennatMoreAccepted);
		result.addObject("tennatMoreDenied", tennatMoreDenied);
		result.addObject("tennatMorePending", tennatMorePending);
		*/
		
		result.addObject("minSocialIdentityPerActor", minSocialIdentityPerActor);
		result.addObject("maxSocialIdentityPerActor", maxSocialIdentityPerActor);
		result.addObject("avgSocialIdentityPerActor", avgSocialIdentityPerActor);
		
		/*result.addObject("minInvoicePerTenant", minInvoicePerActor);
		result.addObject("maxInvoicePerTenant", maxInvoicePerActor);
		result.addObject("avgInvoicePerTenant", avgInvoicePerActor); */
		
		result.addObject("totalAmountMoney", totalAmountMoney);
		
		result.addObject("versusRequestsPropertyAudit", versusRequestsPropertyAudit);
		result.addObject("ratioLessors", ratioLessors);
		result.addObject("ratioTenants", ratioTenants);
		
		return result;
	}
	
 	
	
	
	
}