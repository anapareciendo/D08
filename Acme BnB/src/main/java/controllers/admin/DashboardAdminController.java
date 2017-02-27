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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import controllers.AbstractController;
import domain.Lessor;
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

		
		Double requestAcceptedByLessor= adminService.requestAcceptedByLessor();
		Double requestDeniedByLessor= adminService.requestDeniedByLessor();
		
		Double requestAcceptedByTenant= adminService.requestAcceptedByTenant();
		Double requestDeniedByTenant = adminService.requestDeniedByTenant();
		
		Lessor lessorMoreAccepted = adminService.lessorMoreAccepted();
		Lessor lessorMoreDenied = adminService.lessorMoreDenied();
		Lessor lessorMorePending = adminService.lessorMorePending();
		
		Tenant tennatMoreAccepted = adminService.tennatMoreAccepted();
		Tenant tennatMoreDenied = adminService.tennatMoreDenied();
		Tenant tennatMorePending = adminService.tennatMorePending();
		
		Integer minSocialIdentityPerActor = adminService.minSocialIdentityPerActor();
		Integer maxSocialIdentityPerActor = adminService.maxSocialIdentityPerActor();
		Integer avgSocialIdentityPerActor = adminService.avgSocialIdentityPerActor();
		
		/*Integer minInvoicePerActor = adminService.minInvoicePerActor();
		Integer maxInvoicePerActor = adminService.maxInvoicePerActor();
		Integer avgInvoicePerActor = adminService.avgInvoicePerActor();
		*/
		
		Double totalAmountMoney = adminService.totalAmountMoney();
	
		result = new ModelAndView("administrator/dashboard");
		result.addObject("requestAcceptedByLessor", requestAcceptedByLessor);
		result.addObject("requestDeniedByLessor", requestDeniedByLessor);
		
		result.addObject("requestAcceptedByTenant", requestAcceptedByTenant);
		result.addObject("requestDeniedByTenant", requestDeniedByTenant);
		
		result.addObject("lessorMoreAccepted", lessorMoreAccepted);
		result.addObject("lessorMoreDenied", lessorMoreDenied);
		result.addObject("lessorMorePending", lessorMorePending);
		
		result.addObject("tennatMoreAccepted", tennatMoreAccepted);
		result.addObject("tennatMoreDenied", tennatMoreDenied);
		result.addObject("tennatMorePending", tennatMorePending);
		
		result.addObject("minSocialIdentityPerActor", minSocialIdentityPerActor);
		result.addObject("maxSocialIdentityPerActor", maxSocialIdentityPerActor);
		result.addObject("avgSocialIdentityPerActor", avgSocialIdentityPerActor);
		
		/*result.addObject("minInvoicePerTenant", minInvoicePerActor);
		result.addObject("maxInvoicePerTenant", maxInvoicePerActor);
		result.addObject("avgInvoicePerTenant", avgInvoicePerActor); */
		
		result.addObject("totalAmountMoney", totalAmountMoney);
		
		return result;
	}
	
 	
	
	
	
}