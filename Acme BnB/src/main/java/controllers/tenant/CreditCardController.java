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
	
	/*@RequestMapping(value = "/editCreditCard", method = RequestMethod.POST, params = "save")
	public ModelAndView editCreditCard(@Valid Sponsor sponsor, BindingResult binding) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;
		String show;

		if (binding.hasErrors()) {
			result = createEditModelAndView(sponsor);
		} else {
			try {
				sponsorService.saveEdit(sponsor);				
				
				formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				moment = formatter.format(new Date());
				List<Banner> banners = new ArrayList<Banner>(bannerService.findAllStarCampaignBanners());
				if(!banners.isEmpty()){
					Collections.shuffle(banners);
					Banner banner = banners.get(0);
					banner.setDisplayed(banner.getDisplayed()+1);
					bannerService.save(banner);
					show = banner.getPromo();
				}else{
					show= "";
				}
				result = new ModelAndView("welcome/index");
				result.addObject("name", sponsor.getName());
				result.addObject("moment", moment);
				result.addObject("banner", show);

				result.addObject("message", "actor.commit.well");
				
			} catch (Throwable oops) {
				result = createEditModelAndView(sponsor, "sponsor.commit.error");				
			}
		}

		return result;
	}
	*/
	
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
