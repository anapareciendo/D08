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

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AuditorService;
import services.LessorService;
import services.TenantService;
import domain.Auditor;
import domain.Lessor;
import domain.Tenant;
import forms.ActorForm;

@Controller
@RequestMapping("/security")
public class SigninController extends AbstractController {
	
	// Supporting services ----------------------------------------------------
	@Autowired
	private LessorService lessorService;
	@Autowired
	private TenantService tenantService;
	@Autowired
	private AuditorService auditorService;
	
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
	
	@RequestMapping(value="/signinTenant", method = RequestMethod.GET)
	public ModelAndView signinTenant(){
		ModelAndView result;
		ActorForm actor = new ActorForm();
		
		result = new ModelAndView("security/signin");
		result.addObject("authority", "tenant2");
		result.addObject("tenant2", actor);
		
		return result;
	}
	
	@RequestMapping(value = "/signin", method = RequestMethod.POST, params = "lessor2")
	public ModelAndView user(ActorForm actor, BindingResult binding) {
		ModelAndView result;
		Lessor lessor;
		lessor = lessorService.reconstruct(actor, binding);

		if (binding.hasErrors() || lessor.getName().equals("Pass") || lessor.getName().equals("Cond")) {
			result = new ModelAndView("security/signin");
			result.addObject("lessor2", actor);
			result.addObject("authority", "lessor2");
			if(lessor.getName().equals("Pass")){
				result.addObject("message", "security.password.failed");
			}else if(lessor.getName().equals("Cond")){
				result.addObject("message", "security.condition.failed");
			}
			else{
				result.addObject("errors", binding.getAllErrors());
			}
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
	
	@RequestMapping(value = "/signin", method = RequestMethod.POST, params = "tenant2")
	public ModelAndView tenant(ActorForm actor, BindingResult binding) {
		ModelAndView result;
		Tenant tenant;
		tenant = tenantService.reconstruct(actor, binding);
		
		int years=(actor.getCreditCard().getExpirationYear()+2000)-1970;
		int month=actor.getCreditCard().getExpirationMonth();
		long exp = years*31540000000l+month*2628000000l;
		long finale = exp-Calendar.getInstance().getTime().getTime();
		
		if (binding.hasErrors() || tenant.getName().equals("Pass") || tenant.getName().equals("Cond") || finale<0) {
			result = new ModelAndView("security/signin");
			result.addObject("authority", "tenant2");
			result.addObject("tenant2", actor);
			if(finale<0){
				result.addObject("message", "security.credit.failed");
			}else if(tenant.getName().equals("Pass")){
				result.addObject("message", "security.password.failed");
			}else if(tenant.getName().equals("Cond")){
				result.addObject("message", "security.condition.failed");
			}
			else{
				result.addObject("errors", binding.getAllErrors());
			}
		} else {
			try{
				tenantService.save(tenant);
				result = new ModelAndView("redirect:login.do");
			}catch (Throwable oops) {
				result = new ModelAndView("security/signin");
				result.addObject("authority", "tenant2");
				result.addObject("tenant2", actor);
				result.addObject("message", "security.signin.failed");
			}
		}

		return result;
	}
	
	@RequestMapping(value = "/signin", method = RequestMethod.POST, params = "audit2")
	public ModelAndView auditor(ActorForm actor, BindingResult binding) {
		ModelAndView result;
		Auditor auditor;
		auditor = auditorService.reconstructForm(actor, binding);

		if (binding.hasErrors() || auditor.getName().equals("Pass") || auditor.getName().equals("Cond")) {
			result = new ModelAndView("security/signin");
			result.addObject("lessor2", actor);
			result.addObject("authority", "lessor2");
			if(auditor.getName().equals("Pass")){
				result.addObject("message", "security.password.failed");
			}else if(auditor.getName().equals("Cond")){
				result.addObject("message", "security.condition.failed");
			}
			else{
				result.addObject("errors", binding.getAllErrors());
			}
		} else {
			try{
				auditorService.save(auditor);
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
