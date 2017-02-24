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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.LessorService;
import domain.Lessor;
import forms.ActorForm;

@Controller
@RequestMapping("/security")
public class SigninController extends AbstractController {
	
	// Supporting services ----------------------------------------------------
	@Autowired
	private LessorService lessorService;
	
	// Constructors -----------------------------------------------------------
	
	public SigninController() {
		super();
	}
	
	
	@RequestMapping(value="/signinLessor", method = RequestMethod.GET)
	public ModelAndView signinUser(){
		ModelAndView result;
		ActorForm actor = new ActorForm();
		
		result = new ModelAndView("security/signin");
		result.addObject("authority", "lessor2");
		result.addObject("lessor2", actor);
		
		return result;
	}
	
	@RequestMapping(value = "/signin", method = RequestMethod.POST, params = "lessor2")
	public ModelAndView user(ActorForm actor, BindingResult binding) {
		ModelAndView result;
		Lessor lessor;
		lessor = lessorService.reconstruct(actor, binding);

		if (binding.hasErrors()) {
			result = new ModelAndView("security/signin");
			result.addObject("lessor2", actor);
			result.addObject("authority", "lessor2");
			result.addObject("errors", binding.getAllErrors());
		} else {
			try{
				lessorService.save(lessor);
				result = new ModelAndView("redirect:login.do");
			}catch (Throwable oops) {
				result = new ModelAndView("security/signin");
				result.addObject("authority", "lessor2");
				result.addObject("lessor2", actor);
				result.addObject("message", "security.signin.failed");
			}
		}

		return result;
	}
	

}
