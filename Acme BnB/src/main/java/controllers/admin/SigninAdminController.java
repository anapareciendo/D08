/*
 * ProfileController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import forms.ActorForm;

@Controller
@RequestMapping("/security/admin")
public class SigninAdminController extends AbstractController {
	
	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------
	
	public SigninAdminController() {
		super();
	}
	
	
	@RequestMapping(value="/audit", method = RequestMethod.GET)
	public ModelAndView signinUser(){
		ModelAndView result;
		ActorForm actor = new ActorForm();
		
		result = new ModelAndView("security/signin");
		result.addObject("authority", "auditor2");
		result.addObject("auditor2", actor);
		
		return result;
	}
	
}
