
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import services.RequestService;

@Controller
@RequestMapping("/request")
public class RequestController extends AbstractController {

	@Autowired
	private RequestService	requestService;


	public RequestController() {
		super();
	}

//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	public ModelAndView list() {
//		ModelAndView result;
//		Collection<Request> request= requestService.findMyRequestProperties();
//
//		result = new ModelAndView("request/list");
//		result.addObject("requestURI", "request/list.do");
//		result.addObject("request", request);
//
//		return result;
//	}
	

}
