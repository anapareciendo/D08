
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.PropertyService;
import domain.Property;

@Controller
@RequestMapping("/property")
public class PropertyController extends AbstractController {

	@Autowired
	private PropertyService	propertyService;


	public PropertyController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Property> property;

		property = propertyService.findAll();
		result = new ModelAndView("property/list");
		result.addObject("requestURI", "property/list.do");
		result.addObject("property", property);

		return result;
	}
	
	@RequestMapping(value = "/listMyProperties", method = RequestMethod.GET)
	public ModelAndView listMyProperties() {
		ModelAndView result;
		Collection<Property> property;

		property = propertyService.findMyProperties();
		result = new ModelAndView("property/list");
		result.addObject("requestURI", "property/list.do");
		result.addObject("property", property);
		result.addObject("owner", true);

		return result;
	}
}
