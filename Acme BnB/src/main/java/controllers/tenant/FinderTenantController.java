/* ProfileController.java
 *
 * Copyright (C) 2015 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers.tenant;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.FinderService;
import services.TenantService;
import controllers.AbstractController;
import domain.Finder;
import domain.Property;

@Controller
@RequestMapping("/finder/tenant")
public class FinderTenantController extends AbstractController {
	
	@Autowired
	private FinderService finderService;
	@Autowired
	private TenantService tenantService;
	
	@RequestMapping(value="/finder", method = RequestMethod.GET)
	public ModelAndView signinUser(){
		ModelAndView result;
		Finder finder= tenantService.findByUserAccountId(LoginService.getPrincipal().getId()).getFinder();
		if(!finderService.isActive()){
			finderService.delete(finder);
			finder = finderService.create(tenantService.findByUserAccountId(LoginService.getPrincipal().getId()));
		}
		
		result = new ModelAndView("finder/finder");
		result.addObject("finder", finder);
		
		return result;
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Finder finder, BindingResult binding) {
		ModelAndView result;
		finder = finderService.reconstruct(finder, binding);
		
		if (binding.hasErrors()) {
			result = new ModelAndView("finder/finder");
			result.addObject("finder", finder);
			result.addObject("errors", binding.getAllErrors());
		} else {
			try {
				Finder ff=finderService.save(finder);				
				Collection<Property> search = finderService.searchFincer(ff.getAddress(), ff.getMinPrice(), ff.getMaxPrice(), ff.getDestinationCity(), ff.getKeyword());
				result = new ModelAndView("property/list");
				result.addObject("requestURI", "property/list.do");
				result.addObject("property", search);
			} catch (Throwable oops) {
				result = new ModelAndView("finder/finder");
				result.addObject("finder", finder);
				result.addObject("message", "finder.commit.error");
			}
		}

		return result;
	}


	

	
}