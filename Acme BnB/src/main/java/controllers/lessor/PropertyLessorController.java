
package controllers.lessor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.LessorService;
import services.PropertyService;
import controllers.AbstractController;
import domain.Lessor;
import domain.Property;

@Controller
@RequestMapping("/property/lessor")
public class PropertyLessorController extends AbstractController {

	@Autowired
	private PropertyService	propertyService;
	@Autowired
	private LessorService lessorService;


	public PropertyLessorController() {
		super();
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Property> property;

		property = propertyService.findMyProperties();
		result = new ModelAndView("property/list");
		result.addObject("requestURI", "property/list.do");
		result.addObject("property", property);
		result.addObject("owner", true);

		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Property property;

		Lessor lessor = lessorService.findByUserAccountId(LoginService.getPrincipal().getId());
		
		property = propertyService.create(lessor);
		result = createEditModelAndView(property);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int propertyId) {
		ModelAndView result;
		
		Property property = propertyService.findOne(propertyId);
				
		result = createEditModelAndView(property);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Property property, BindingResult binding) {
		ModelAndView result;
		System.out.println("Errores"+binding.hasErrors());
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(property);
			result.addObject("errors", binding.getAllErrors());
		} else {
			try {
				property = propertyService.reconstruct(property, binding);
				propertyService.save(property);				
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(property, "property.commit.error");
			}
		}

		return result;
	}
	
	protected ModelAndView createEditModelAndView(Property property) {
		ModelAndView result;

		result = createEditModelAndView(property, null);
		
		return result;
	}	
	
	protected ModelAndView createEditModelAndView(Property property, String message) {
		ModelAndView result;
		
		result = new ModelAndView("property/edit");
		result.addObject("property", property);
		result.addObject("message", message);

		return result;
	}
}
