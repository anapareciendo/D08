
package controllers.lessor;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RequestService;
import controllers.AbstractController;
import domain.Request;
import domain.Status;

@Controller
@RequestMapping("/request/lessor")
public class RequestLessorController extends AbstractController {

	@Autowired
	private RequestService	requestService;


	public RequestLessorController() {
		super();
	}

	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept() {
		ModelAndView result;
		Collection<Request> requests;
		requests=requestService.findMyRequestDeniedProperties();
		result = new ModelAndView("request/list");
		result.addObject("requestURI", "request/list.do");
		result.addObject("req", requests);
		result.addObject("accept", true);

		return result;
	}
	
	@RequestMapping(value = "/denied", method = RequestMethod.GET)
	public ModelAndView denied() {
		ModelAndView result;
		Collection<Request> requests;
		requests=requestService.findMyRequestAcceptProperties();
		result = new ModelAndView("request/list");
		result.addObject("requestURI", "request/list.do");
		result.addObject("req", requests);
		result.addObject("accept", false);

		return result;
	}
	
	@RequestMapping(value = "/toAccept", method = RequestMethod.GET)
	public ModelAndView toAccept(@RequestParam int requestId){
		ModelAndView result;
		
		Collection<Request> requests;
		requests=requestService.findMyRequestDeniedProperties();
		result = new ModelAndView("request/list");
		result.addObject("requestURI", "request/list.do");
		result.addObject("req", requests);
		result.addObject("accept", true);
		
		Request request = requestService.findOne(requestId);
		
		int years=(request.getCreditCard().getExpirationYear()+2000)-1970;
		int month=request.getCreditCard().getExpirationMonth();
		long exp = years*31540000000l+month*2628000000l;
		long finale = exp-Calendar.getInstance().getTime().getTime();
		
		if(finale>0){
			request.setStatus(Status.ACCEPTED);
		}else{
			request.setStatus(Status.DENIED);
			result.addObject("message", "request.error.credit");
		}
		
		requestService.save(request);
		
		
		
		return result;
	}
	
	@RequestMapping(value = "/toDeny", method = RequestMethod.GET)
	public ModelAndView toDeny(@RequestParam int requestId){
		ModelAndView result;
		
		Request request = requestService.findOne(requestId);
		request.setStatus(Status.DENIED);
		requestService.save(request);
		
		result = new ModelAndView("redirect:denied.do");
		
		return result;
	}
	

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Request> request= requestService.findMyRequestLessor();

		result = new ModelAndView("request/list");
		result.addObject("requestURI", "request/list.do");
		result.addObject("req", request);

		return result;
	}
	
	

}
