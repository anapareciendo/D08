
package controllers.lessor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RequestService;
import controllers.AbstractController;
import domain.Request;

@Controller
@RequestMapping("/request/lessor")
public class RequestLessorController extends AbstractController {

	@Autowired
	private RequestService	requestService;


	public RequestLessorController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Request> requests;
		requests=requestService.findMyRequestProperties();
		result = new ModelAndView("request/list");
		result.addObject("requestURI", "request/list.do");
		result.addObject("req", requests);

		return result;
	}
	

}
