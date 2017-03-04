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
import services.AuditorService;
import domain.Auditor;

@Controller
@RequestMapping("/auditor")
public class AuditorController extends AbstractController {

	@Autowired
	private AuditorService auditorService;
	// Constructors -----------------------------------------------------------
	
	public AuditorController() {
		super();
	}
		
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
			ModelAndView result;
			
			Auditor auditor= auditorService.findByUserAccountId(LoginService.getPrincipal().getId());

			result = new ModelAndView("auditor/edit");
			result.addObject("auditor", auditor);
			
			return result;
		
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Auditor auditor, BindingResult binding) {
		ModelAndView result;
		try{
			auditor= auditorService.reconstruct(auditor, binding);
			try {
				auditorService.save(auditor);				
				result = new ModelAndView("welcome/index");
				result.addObject("message", "auditor.commit.ok");
			} catch (Throwable oops) {
				result = createEditModelAndView(auditor, "auditor.commit.error");
				result.addObject("errors", binding.getAllErrors());
			}
		}catch(Throwable oppss){
			result = createEditModelAndView(auditor);
			//result.addObject("errors", binding.getAllErrors());
		}

		return result;
	}
	
	protected ModelAndView createEditModelAndView(Auditor auditor) {
		ModelAndView result;

		result = createEditModelAndView(auditor, null);
		
		return result;
	}	
	
	protected ModelAndView createEditModelAndView(Auditor auditor, String message) {
		ModelAndView result;
		
		result = new ModelAndView("auditor/edit");
		result.addObject("auditor", auditor);
		result.addObject("message", message);

		return result;
	}

	
}