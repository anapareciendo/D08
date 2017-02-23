package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

}
