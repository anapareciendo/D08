
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
		result.addObject("value", value);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int valueId) {
		ModelAndView result;
		Collection<Attribute> attributes = attributeService.findAll();
		Value value = valueService.findOne(valueId);
		
		result = createEditModelAndView(value);
		result.addObject("attributes", attributes);

		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int propertyId) {
		ModelAndView result;
		Value value;
		
		Property property = propertyService.findOne(propertyId);
		
		List<Attribute> attributes = new ArrayList<Attribute>();
		attributes.addAll(attributeService.findAll());
		
		value = valueService.create(property, attributes.get(0));
		
		result = createEditModelAndView(value);
		

		return result;
	}
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Value value, BindingResult binding, @RequestParam int propertyId) {
		ModelAndView result;
		try {
			value = valueService.reconstruct(value, binding, propertyId);
			try{	
				valueService.save(value);
				result = new ModelAndView("redirect:list.do?propertyId="+propertyId);
				 
			} catch (Throwable oops) {
				result = createEditModelAndView(value,"value.commit.error");
				
			}
		}catch(Throwable oppss){
			result = createEditModelAndView(value);
//			result.addObject("errors", binding.getAllErrors());
		}
		

		return result;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int valueId) {
		ModelAndView result;
		
		Value value = valueService.findOne(valueId);
		
		try {
			int propertyId = value.getProperty().getId();
			valueService.delete(value);
			result = new ModelAndView("redirect:list.do?propertyId="+propertyId);	
		} catch (Throwable oops) {
			result = createEditModelAndView(value, "value.commit.error");
		}

		return result;
	}
	
	protected ModelAndView createEditModelAndView(Value value) {
		ModelAndView result;

		result = createEditModelAndView(value, null);
		
		return result;
	}	
	
	protected ModelAndView createEditModelAndView(Value value, String message) {
		ModelAndView result;
				
		result = new ModelAndView("value/edit");
		result.addObject("value", value);
		result.addObject("message", message);

		return result;
	}

}
