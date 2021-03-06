/* CurriculumCustomerController.java
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers.admin;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AttributeService;
import controllers.AbstractController;
import domain.Attribute;

@Controller
@RequestMapping("/attribute/admin")
public class AttributeAdminController extends AbstractController {

	@Autowired
	private AttributeService attributeService;

	
	public AttributeAdminController() {
		super();
	}
	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Attribute> attribute;

		attribute = attributeService.findAll();
		result = new ModelAndView("attribute/list");
		result.addObject("requestURI", "attribute/admin/list.do");
		result.addObject("attribute", attribute);

		return result;
	}
		
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int attributeId) {
	ModelAndView result;
		
		try{
		Attribute attribute= attributeService.findOne(attributeId);
		attributeService.delete(attribute);
		
		Collection<Attribute> attributes= attributeService.findAll();
		result = new ModelAndView("attribute/list");
		result.addObject("requestURI", "attribute/admin/list.do");
		result.addObject("attribute", attributes);
		}catch(Throwable oops){
			Collection<Attribute> attributes= attributeService.findAll();
			result = new ModelAndView("attribute/list");
			result.addObject("requestURI", "attribute/admin/list.do");
			result.addObject("attribute", attributes);
			result.addObject("message", "attribute.delete.error");
		}
		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Attribute attribute= attributeService.create();
		result = new ModelAndView("attribute/edit");
		result.addObject("attribute", attribute);
		
		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Attribute attribute, BindingResult binding) {
		ModelAndView result;

		try {
			attribute = attributeService.reconstruct(attribute, binding);
			try {
				attributeService.save(attribute);				
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(attribute, "attribute.commit.error");				
			}
		}catch(Throwable oops){
			result = createEditModelAndView(attribute);
			result = new ModelAndView("attribute/edit");
			result.addObject("attribute",attribute);
		}	

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int attributeId) {
		ModelAndView result;
		Attribute attribute= attributeService.findOne(attributeId);

		result = new ModelAndView("attribute/edit");
		result.addObject("attribute", attribute);
		
		
		return result;
	}
	
	
	protected ModelAndView createEditModelAndView(Attribute attribute) {
		ModelAndView result;

		result = createEditModelAndView(attribute, null);
		
		return result;
	}	
	
	protected ModelAndView createEditModelAndView(Attribute attribute, String message) {
		ModelAndView result;
		
		result = new ModelAndView("attribute/list");
		result.addObject("attribute", attribute);
		result.addObject("message", message);

		return result;
	}
}