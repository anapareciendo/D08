
package controllers.auditor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AuditService;
import services.AuditorService;
import services.PropertyService;
import controllers.AbstractController;
import domain.Audit;
import domain.Property;

@Controller
@RequestMapping("/audit/auditor")
public class AuditAuditorController extends AbstractController {

	@Autowired
	private AuditService	auditService;

	@Autowired
	private AuditorService auditorService;
	
	@Autowired
	private PropertyService propertyService;

	public AuditAuditorController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Audit> audit;

		audit = auditService.findMyDraftAudits(LoginService.getPrincipal().getId());
		
		result = new ModelAndView("audit/list");
		result.addObject("requestURI", "audit/list.do");
		result.addObject("audit", audit);
		result.addObject("owner", true);

		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		List<Property> properties = new ArrayList<Property>();
		properties.addAll(propertyService.findAll());
		properties.removeAll(propertyService.findNotAuditProperty());
		
		result = new ModelAndView("audit/create");
		if(properties.isEmpty()){
			result.addObject("vacio", true);
		}else{
			Audit audit = auditService.create(auditorService.findByUserAccountId(LoginService.getPrincipal().getId()), properties.get(0));
			result.addObject("audit", audit);
			result.addObject("properties", properties);
		}
		return result;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "draft")
	public ModelAndView draft(Audit audit, BindingResult binding) {
		ModelAndView result;
		try{
			audit = auditService.reconstruct(audit, binding);
			try {
				auditService.save(audit);				
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				List<Property> properties = new ArrayList<Property>();
				properties.addAll(propertyService.findAll());
				properties.removeAll(propertyService.findNotAuditProperty());
				
				result = new ModelAndView("audit/create");
				result.addObject("audit", audit);
				result.addObject("properties", properties);
				result.addObject("message", "audit.commit.error");
			}
		}catch(Throwable oppss){
			result = new ModelAndView("audit/create");
			List<Property> properties = new ArrayList<Property>();
			properties.addAll(propertyService.findAll());
			properties.removeAll(propertyService.findNotAuditProperty());
			
			result.addObject("audit", audit);
			result.addObject("properties", properties);
//			result.addObject("errors", binding.getAllErrors());
		}

		return result;
	}
	
	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public ModelAndView send(@RequestParam int auditId) {
		ModelAndView result;
		
		Audit audit = auditService.findOne(auditId);
		audit.setDraft(false);
		auditService.save(audit);
		result = new ModelAndView("redirect:list.do");
		
		return result;
	}
	
	
	

}
