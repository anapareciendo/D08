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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
			result= new ModelAndView("hacker/hackers");
		}
		return result;
	}
}