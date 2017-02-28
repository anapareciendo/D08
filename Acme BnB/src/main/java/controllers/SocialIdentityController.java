package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SocialIdentityService;
import domain.SocialIdentity;

@Controller
@RequestMapping("/socialIdentity")
public class SocialIdentityController extends AbstractController{

	@Autowired
	private SocialIdentityService socialIdentityService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<SocialIdentity> socialIdentities;

		socialIdentities = socialIdentityService.findAll();
		result = new ModelAndView("socialIdentity/list");
		result.addObject("requestURI", "socialIdentity/list.do");
		result.addObject("socialIdentity", socialIdentities);

		return result;
	}

}
