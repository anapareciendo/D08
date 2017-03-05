/* AdministratorController.java
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.TenantService;
import domain.Tenant;

@Controller
@RequestMapping("/tenant")
public class TenantController extends AbstractController {

	@Autowired
	private TenantService tenantService;
	// Constructors -----------------------------------------------------------
	
	public TenantController() {
		super();
	}
		
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Tenant tenant= tenantService.findByUserAccountId(LoginService.getPrincipal().getId());
		try{
		tenant = tenantService.findOne(tenant.getId());
		result = new ModelAndView("tenant/display");
		result.addObject("tenant", tenant);
		}catch(Throwable oops){
			
			result= new ModelAndView("hacker/hackers");

		}
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
			ModelAndView result;
			
			Tenant tenant= tenantService.findByUserAccountId(LoginService.getPrincipal().getId());

			result = new ModelAndView("tenant/edit");
			result.addObject("tenant", tenant);
			
			return result;
		
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Tenant tenant, BindingResult binding) {
		ModelAndView result;
		try{
			tenant= tenantService.reconstruct(tenant, binding);
			try {
				tenantService.save(tenant);				
				result = new ModelAndView("welcome/index");
				result.addObject("message", "tenant.commit.ok");
			} catch (Throwable oops) {
				result = createEditModelAndView(tenant, "tenant.commit.error");
				result.addObject("errors", binding.getAllErrors());
			}
		}catch(Throwable oppss){
			result = createEditModelAndView(tenant);
			//result.addObject("errors", binding.getAllErrors());
		}

		return result;
	}
	
	protected ModelAndView createEditModelAndView(Tenant tenant) {
		ModelAndView result;

		result = createEditModelAndView(tenant, null);
		
		return result;
	}	
	
	protected ModelAndView createEditModelAndView(Tenant tenant, String message) {
		ModelAndView result;
		
		result = new ModelAndView("tenant/edit");
		result.addObject("tenant", tenant);
		result.addObject("message", message);

		return result;
	}

	
}