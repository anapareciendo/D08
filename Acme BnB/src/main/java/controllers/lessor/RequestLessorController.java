
package controllers.lessor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.LessorService;
import services.RequestService;
import controllers.AbstractController;
import domain.Lessor;
import domain.Request;
import domain.Status;

@Controller
@RequestMapping("/request/lessor")
public class RequestLessorController extends AbstractController {

	@Autowired
	private RequestService	requestService;
	@Autowired
	private LessorService lessorService;


	public RequestLessorController() {
		super();
	}

	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept() {
		ModelAndView result;
		Lessor lessor = lessorService.findByUserAccountId(LoginService.getPrincipal().getId());
		
		int years=(lessor.getCreditCard().getExpirationYear()+2000)-1970;
		int month=lessor.getCreditCard().getExpirationMonth();
		long exp = years*31540000000l+month*2628000000l;
		long finale = exp-Calendar.getInstance().getTime().getTime();
		
		if(finale>0){
			Collection<Request> requests;
			requests=requestService.findMyRequestDeniedProperties();
			result = new ModelAndView("request/list");
			result.addObject("requestURI", "request/list.do");
			result.addObject("req", requests);
			result.addObject("accept", true);
		}else{
			SimpleDateFormat formatter;
			String moment;
			
			formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			moment = formatter.format(new Date());
					
			result = new ModelAndView("welcome/index");
			result.addObject("name", lessor.getName());
			result.addObject("moment", moment);
			result.addObject("message", "request.lessor.no.card");
		}

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
		
		result = new ModelAndView("request/list");
		result.addObject("requestURI", "request/list.do");
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
		
		requests=requestService.findMyRequestDeniedProperties();
		result.addObject("req", requests);
		
		
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
