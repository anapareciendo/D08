package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import services.LessorService;
import services.SocialIdentityService;
import services.TenantService;
import domain.Lessor;
import domain.SocialIdentity;
import domain.Tenant;

@Controller
@RequestMapping("/socialIdentity")
public class SocialIdentityController extends AbstractController{

	@Autowired
	private SocialIdentityService socialIdentityService;
	@Autowired
	private LessorService lessorService;
	@Autowired
	private TenantService tenantService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<SocialIdentity> socialIdentities;

		socialIdentities = socialIdentityService.findMySocialIdentities();
		result = new ModelAndView("socialIdentity/list");
		result.addObject("requestURI", "socialIdentity/list.do");
		result.addObject("socialIdentity", socialIdentities);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int socialIdentityId) {
		ModelAndView result;
		
		SocialIdentity socialIdentity= socialIdentityService.findOne(socialIdentityId);
				
		result = createEditModelAndView(socialIdentity);

		return result;
	}
	

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		SocialIdentity socialIdentity=new SocialIdentity();
		//Tenant
		Authority t = new Authority();
		t.setAuthority(Authority.TENANT);
		//Lessor
		Authority l = new Authority();
		l.setAuthority(Authority.LESSOR);

		UserAccount ua= LoginService.getPrincipal();
		if(ua.getAuthorities().contains(l)){
			Lessor lessor = lessorService.findByUserAccountId(LoginService.getPrincipal().getId());
			socialIdentity = socialIdentityService.create(lessor);
			result = createEditModelAndView(socialIdentity);
		}else if(ua.getAuthorities().contains(t)){
			Tenant tenant= tenantService.findByUserAccountId(LoginService.getPrincipal().getId());
			socialIdentity = socialIdentityService.create(tenant);
			result = createEditModelAndView(socialIdentity);
		}
		result = createEditModelAndView(socialIdentity);
		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(SocialIdentity socialIdentity, BindingResult binding) {
		ModelAndView result;
		
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(socialIdentity);
			result.addObject("errors", binding.getAllErrors());
		} else {
			try {
				socialIdentity = socialIdentityService.reconstruct(socialIdentity, binding);
				socialIdentityService.save(socialIdentity);				
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				
				result = createEditModelAndView(socialIdentity, "socialIdentity.commit.error");
			}
		}

		return result;
	}
	
	
	protected ModelAndView createEditModelAndView(SocialIdentity socialIdentity) {
		ModelAndView result;

		result = createEditModelAndView(socialIdentity, null);
		
		return result;
	}	
	
	protected ModelAndView createEditModelAndView(SocialIdentity socialIdentity, String message) {
		ModelAndView result;
		
		result = new ModelAndView("socialIdentity/edit");
		result.addObject("socialIdentity", socialIdentity);
		result.addObject("message", message);

		return result;
	}

}
