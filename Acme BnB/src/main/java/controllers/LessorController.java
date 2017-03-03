package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.LessorService;
import domain.Lessor;

@Controller
@RequestMapping("/lessor")
public class LessorController {
	
	@Autowired
	private LessorService lessorService;

	
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int lessorId) {
		ModelAndView result;
		Lessor lessor;
		try{
		lessor = lessorService.findOne(lessorId);
		result = new ModelAndView("lessor/display");
		result.addObject("lessor", lessor);
		}catch(Throwable oops){
			
			result= new ModelAndView("hacker/hackers");

		}
		return result;
	}
	


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
			ModelAndView result;
			
			Lessor lessor= lessorService.findByUserAccountId(LoginService.getPrincipal().getId());

			result = new ModelAndView("lessor/edit");
			result.addObject("lessor", lessor);
			
			return result;
		
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Lessor lessor, BindingResult binding) {
		ModelAndView result;
		try{
			lessor= lessorService.reconstruct2(lessor, binding);
			//lessor = lessorService.reconstruct2(lessor, binding);
			try {
				lessorService.save(lessor);				
				result = new ModelAndView("welcome/index");
				result.addObject("message", "lessor.commit.ok");
			} catch (Throwable oops) {
				result = createEditModelAndView(lessor, "lessor.commit.error");
				result.addObject("errors", binding.getAllErrors());
			}
		}catch(Throwable oppss){
			result = createEditModelAndView(lessor);
			result.addObject("errors", binding.getAllErrors());
		}

		return result;
	}
	
	protected ModelAndView createEditModelAndView(Lessor lessor) {
		ModelAndView result;

		result = createEditModelAndView(lessor, null);
		
		return result;
	}	
	
	protected ModelAndView createEditModelAndView(Lessor lessor, String message) {
		ModelAndView result;
		
		result = new ModelAndView("lessor/edit");
		result.addObject("lessor", lessor);
		result.addObject("message", message);

		return result;
	}
}
