
package controllers.tenant;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RequestService;
import controllers.AbstractController;
import domain.Request;
import forms.RequestForm;

@Controller
@RequestMapping("/request/tenant")
public class RequestTenantController extends AbstractController {

	@Autowired
	private RequestService	requestService;

	public RequestTenantController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Request> request= requestService.findMyRequest();

		result = new ModelAndView("request/list");
		result.addObject("requestURI", "request/tenant/list.do");
		result.addObject("req", request);

		return result;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int propertyId) {
		ModelAndView result;
		RequestForm request = new RequestForm();
		request.setPropertyId(propertyId);
			
		result = new ModelAndView("request/create");
		result.addObject("req", request);
		return result;
	}
	
	@RequestMapping(value = "/make", method = RequestMethod.POST, params = "save")
	public ModelAndView save(RequestForm request, BindingResult binding) {
		ModelAndView result;
		
		try{
			Request req = requestService.reconstruct(request, binding);
			if(req.getId()==-1){
				result = new ModelAndView("request/create");
				request.setCheckIn(null);
				request.setCheckOut(null);
				result.addObject("req", request);
				result.addObject("message", "finder.error.date");
			}else{
				try {
					requestService.save(req);
					result = new ModelAndView("redirect:list.do");
				} catch (Throwable oops) {
					result = new ModelAndView("request/create");
					request.setCheckIn(null);
					request.setCheckOut(null);
					result.addObject("req", request);
					result.addObject("errors", binding.getAllErrors());
				}
			}
		}catch(Throwable oopss){
			result = new ModelAndView("request/create");
			request.setCheckIn(null);
			request.setCheckOut(null);
			result.addObject("req", request);
			result.addObject("message", "finder.commit.error");
		}
		
		return result;
	}
	

}
