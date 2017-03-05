/* AdministratorController.java
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.CommentService;
import services.LessorService;
import services.TenantService;
import domain.Actor;
import domain.Comment;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	@Autowired
	private LessorService lessorService;
	
	@Autowired
	private TenantService tenantService;
	
	@Autowired
	private CommentService commentService;
	// Constructors -----------------------------------------------------------
	
	public ActorController() {
		super();
	}
		
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		
		int uaId=LoginService.getPrincipal().getId();
		Actor actor = lessorService.findByUserAccountId(uaId);
		
		if(actor == null){
			actor = tenantService.findByUserAccountId(uaId);
		}
		
		Collection<Comment> comment;

		comment = commentService.findProfileComments(actor.getId());
		result = new ModelAndView("actor/display");
		result.addObject("actor", actor);
		result.addObject("comment", comment);
		
		return result;
	}

	
}