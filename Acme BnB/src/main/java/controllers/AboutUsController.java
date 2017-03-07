/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/aboutUs")
public class AboutUsController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public AboutUsController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/acme", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		//Tenant tenant= tenantService.findByUserAccountId(LoginService.getPrincipal().getId());
		try {
			//tenant = tenantService.findOne(tenant.getId());
			result = new ModelAndView("welcome/aboutUs");
			//result.addObject("tenant", tenant);
		} catch (Throwable oops) {

			result = new ModelAndView("hacker/hackers");

		}
		return result;
	}
}
