/*
 * ProfileController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.UserAccount;
import security.UserAccountService;
import services.LessorService;
import domain.Lessor;

@Controller
@RequestMapping("/security")
public class SigninController extends AbstractController {
	
	// Supporting services ----------------------------------------------------
	@Autowired
	private LessorService lessorService;
	@Autowired
	private UserAccountService userAccountService;
	
	
	// Constructors -----------------------------------------------------------
	
	public SigninController() {
		super();
	}
	
	
	@RequestMapping(value="/signinLessor", method = RequestMethod.GET)
	public ModelAndView signinUser(){
		ModelAndView result;
		Lessor actor = lessorService.create();
		
		UserAccount account = userAccountService.create();
		Authority a = new Authority();
		a.setAuthority(Authority.LESSOR);
		account.getAuthorities().add(a);
		actor.setUserAccount(account);
		
		result = new ModelAndView("security/signin");
		result.addObject("authority", "lessor2");
		result.addObject("lessor2", actor);
		
		return result;
	}
	

}
