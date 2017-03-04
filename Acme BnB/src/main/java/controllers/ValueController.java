
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AttributeService;
import services.PropertyService;
import services.ValueService;
import domain.Attribute;
import domain.Property;
import domain.Value;

@Controller
@Transactional
@RequestMapping("/value")
public class ValueController extends AbstractController {

	@Autowired
	private ValueService valueService;
	
	@Autowired
	private PropertyService propertyService;
	@Autowired
	private AttributeService attributeService;

	public ValueController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int propertyId) {
		ModelAndView result;
		Collection<Value> value;

		value = valueService.findValues(propertyId);
		result = new ModelAndView("value/list");
		result.addObject("requestURI", "value/list.do");
		if(value.isEmpty()){
			result.addObject("vacio", true);
		}else{
			result.addObject("value", value);
		}
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int valueId) {
		ModelAndView result;
		Collection<Attribute> attributes = attributeService.findAll();
		Value value = valueService.findOne(valueId);
		
		result = new ModelAndView("value/edit");
		result.addObject("value", value);
		result.addObject("attributes",attributes);

		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int propertyId) {
		ModelAndView result;
		List<Attribute> attributes = new ArrayList<Attribute>();
		attributes.addAll(attributeService.findAll());
		Property property = propertyService.findOne(propertyId);
		
		Value value = valueService.create(property, attributes.get(0));
		
		result = new ModelAndView("value/edit");
		result.addObject("value", value);
		result.addObject("attributes",attributes);

		return result;
	}
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Value value, BindingResult binding) {
		ModelAndView result;
		
		Value res = valueService.reconstruct(value, binding);
		if(!binding.hasErrors()){
			try{	
				valueService.save(res);
				result = new ModelAndView("redirect:list.do?propertyId="+value.getProperty().getId());
					 
			} catch (Throwable oops) {
					
				Collection<Attribute> attributes = attributeService.findAll();
				result = new ModelAndView("value/edit");
				result.addObject("value", value);
				result.addObject("attributes",attributes);
				result.addObject("message", "value.commit.error");
					
				}
		}else{
			Collection<Attribute> attributes = attributeService.findAll();
			result = new ModelAndView("value/edit");
			result.addObject("value", value);
			result.addObject("attributes",attributes);
//			result.addObject("errors", binding.getAllErrors());
		}
			
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Value value, BindingResult binding) {
		ModelAndView result;
		
			try {
				int propertyId = value.getProperty().getId();
				valueService.delete(value);
				result = new ModelAndView("redirect:list.do?propertyId="+propertyId);
			} catch (Throwable oops) {
			
				Collection<Attribute> attributes = attributeService.findAll();
				result = new ModelAndView("value/edit");
				result.addObject("value", value);
				result.addObject("attributes",attributes);
				result.addObject("message", "value.commit.error");
			}

		return result;
	}
	
}
