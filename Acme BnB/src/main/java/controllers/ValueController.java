
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

}
