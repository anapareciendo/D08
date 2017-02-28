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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.FinderService;
import services.TenantService;
import controllers.AbstractController;
import domain.Finder;

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
		Finder finder = finderService.create(tenantService.findByUserAccountId(LoginService.getPrincipal().getId()));
		
		result = new ModelAndView("finder/finder");
		result.addObject("finder", finder);
		
		return result;
	}


	

	
}