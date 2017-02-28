
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ValueService;
import domain.Value;

@Controller
@RequestMapping("/value")
public class ValueController extends AbstractController {

	@Autowired
	private ValueService valueService;

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
		
		Value value = valueService.findOne(valueId);
		
		result = createEditModelAndView(value);
		result.addObject("create",false);

		return result;
	}
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Value value, BindingResult binding) {
		ModelAndView result;
		value = valueService.reconstruct(value, binding);
		
		if (binding.hasErrors()) {
			
			result = createEditModelAndView(value);
			result.addObject("errors", binding.getAllErrors());
			
		} else {
			try {
				valueService.save(value);
				
				Collection<Value> values;
				values = valueService.findValues(value.getProperty().getId());
				result = new ModelAndView("value/list");
				result.addObject("requestURI", "value/list.do");
				result.addObject("value", values);
				
			} catch (Throwable oops) {
				result = createEditModelAndView(value, "value.commit.error");
			}
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
