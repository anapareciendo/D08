
package controllers.tenant;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.PropertyService;
import services.RequestService;
import services.TenantService;
import controllers.AbstractController;
import domain.Request;
import domain.Tenant;

@Controller
@RequestMapping("/request/tenant")
public class RequestTenantController extends AbstractController {

	@Autowired
	private RequestService	requestService;
	@Autowired
	private TenantService tenantService;
	@Autowired
	private PropertyService propertyService;


	public RequestTenantController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Request> request= requestService.findMyRequest();

		result = new ModelAndView("request/list");
		result.addObject("requestURI", "request/list.do");
		result.addObject("req", request);

		return result;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int propertyId) {
		ModelAndView result;
		Tenant tenant=tenantService.findByUserAccountId(LoginService.getPrincipal().getId());
			Request request = requestService.create(tenant, propertyService.findOne(propertyId));
			
			result = new ModelAndView("request/create");
			result.addObject("req", request);
		return result;
	}
	

}
