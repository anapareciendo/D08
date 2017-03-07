/* CurriculumCustomerController.java
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FeeService;
import controllers.AbstractController;
import domain.Fee;

// TODO: implement this controller from scratch. 

@Controller
@RequestMapping("/fee/admin")
public class FeeAdminController extends AbstractController {

	@Autowired
	private FeeService feeService;

	
	public FeeAdminController() {
		super();
	}
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		Fee fee= feeService.getFee();
		
		result = new ModelAndView("fee/edit");
		result.addObject("fee", fee);

		return result;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST,  params = "save")
	public ModelAndView save(Fee fee, BindingResult binding) {
		ModelAndView result;
		
		try{
			Fee res=feeService.reconstruc(fee, binding);
			try{
				feeService.save(res);
				result = new ModelAndView("fee/edit");
				result.addObject("fee", res);
				result.addObject("message", "fee.commit.success");
			}catch(Throwable oops){
				result = new ModelAndView("fee/edit");
				result.addObject("fee", res);
				result.addObject("message", "fee.commit.error");
			}
		}catch(Throwable opps){
			result = new ModelAndView("fee/edit");
			result.addObject("fee", fee);
		}
		

		return result;
	}
	
	
}