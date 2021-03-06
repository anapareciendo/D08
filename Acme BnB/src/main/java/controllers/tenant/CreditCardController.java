package controllers.tenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.TenantService;
import controllers.AbstractController;
import domain.Tenant;

@Controller
@RequestMapping("/creditCard")
public class CreditCardController extends AbstractController{
	
	@Autowired
	private TenantService tenantService;



	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView creditCard() {
		ModelAndView result;

		Tenant tenant= tenantService.findByUserAccountId(LoginService.getPrincipal().getId());
	
		result = new ModelAndView("creditCard/edit");
		result.addObject("tenant", tenant);

		return result;
	}

	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Tenant tenant, BindingResult binding) {
		ModelAndView result;
		try{
			tenant = tenantService.reconstruct2(tenant, binding);
			try {
				tenantService.save(tenant);				
				result = new ModelAndView("welcome/index");
				result.addObject("message", "lessor.commit.ok");
			} catch (Throwable oops) {
				result = createEditModelAndView(tenant, "tenant.commit.error");
			}
		}catch(Throwable oppss){
			result = createEditModelAndView(tenant);
		}
//			result.addObject("errors", binding.getAllErrors());
		return result;
		}
		
		
	protected ModelAndView createEditModelAndView(Tenant tenant) {
		ModelAndView result;

		result = createEditModelAndView(tenant, null);
		
		return result;
	}	
	
	protected ModelAndView createEditModelAndView(Tenant tenant, String message) {
		ModelAndView result;
		
		result = new ModelAndView("creditCard/edit");
		result.addObject("tenant", tenant);
		result.addObject("message", message);

		return result;
	}

}
