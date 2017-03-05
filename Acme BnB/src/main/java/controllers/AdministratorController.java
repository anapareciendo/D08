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
import services.AdministratorService;
import domain.Administrator;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private AdministratorService adminService;
	// Constructors -----------------------------------------------------------
	
	public AdministratorController() {
		super();
	}
		
	
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Administrator administrator= adminService.findByUserAccountId(LoginService.getPrincipal().getId());
		try{
		administrator = adminService.findOne(administrator.getId());
		result = new ModelAndView("administrator/display");
		result.addObject("administrator", administrator);
		}catch(Throwable oops){
			
			result= new ModelAndView("hacker/hackers");

		}
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
			ModelAndView result;
			
			Administrator admin= adminService.findByUserAccountId(LoginService.getPrincipal().getId());

			result = new ModelAndView("administrator/edit");
			result.addObject("admin", admin);
			
			return result;
		
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Administrator admin, BindingResult binding) {
		ModelAndView result;
		try{
			admin= adminService.reconstruct(admin, binding);
			try {
				adminService.save(admin);				
				result = new ModelAndView("welcome/index");
				result.addObject("message", "admin.commit.ok");
			} catch (Throwable oops) {
				result = createEditModelAndView(admin, "admin.commit.error");
				result.addObject("errors", binding.getAllErrors());
			}
		}catch(Throwable oppss){
			result = createEditModelAndView(admin);
			result.addObject("errors", binding.getAllErrors());
		}

		return result;
	}
	
	protected ModelAndView createEditModelAndView(Administrator admin) {
		ModelAndView result;

		result = createEditModelAndView(admin, null);
		
		return result;
	}	
	
	protected ModelAndView createEditModelAndView(Administrator admin, String message) {
		ModelAndView result;
		
		result = new ModelAndView("administrator/edit");
		result.addObject("admin", admin);
		result.addObject("message", message);

		return result;
	}

	
}