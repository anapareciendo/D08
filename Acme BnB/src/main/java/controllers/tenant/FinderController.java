/* ProfileController.java
 *
 * Copyright (C) 2015 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers.tenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FinderService;
import controllers.AbstractController;
import domain.Finder;

@Controller
@RequestMapping("/tenant/finder")
public class FinderController extends AbstractController {
	
	@Autowired
	private FinderService finderService;


	
	// Action-1 ---------------------------------------------------------------		

	@RequestMapping(value="/display", method = RequestMethod.GET)
	public ModelAndView displayFinder() {
		ModelAndView result;
		Finder finder = finderService.findMyFinder();

		if(finder!=null){
			result = new ModelAndView("finder/display");
			result.addObject("finder", finder);
			result.addObject("contain", true);
		}else{
			result = new ModelAndView("curricula/display");
			result.addObject("contain", false);
		}
		
		return result;
	}
	

	
}