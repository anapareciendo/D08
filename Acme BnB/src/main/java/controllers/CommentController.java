
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	@Autowired
	private CommentService	commentService;
	
	@Autowired
	private LessorService lessorService;
	
	@Autowired
	private TenantService tenantService;


	public CommentController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Comment> comment;

		comment = commentService.findMyComments();
		result = new ModelAndView("comment/list");
		result.addObject("requestURI", "comment/list.do");
		result.addObject("comment", comment);

		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		
		int uaId=LoginService.getPrincipal().getId();
		Collection<Actor> commentables = new ArrayList<Actor>();
		Actor actor = lessorService.findByUserAccountId(uaId);
		
		if(actor == null){
			actor = tenantService.findByUserAccountId(uaId);
			commentables.add(actor);
			commentables.addAll(lessorService.findMyLessors(actor.getId()));
			
		}else{
			commentables.add(actor);
			commentables.addAll(tenantService.findMyTenants(actor.getId()));
		}
		
		
		Comment comment = commentService.create(actor, actor);
		
		result = new ModelAndView("comment/create");
		result.addObject("comment",comment);
		result.addObject("commentables", commentables);

		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(@Valid Comment comment, BindingResult binding) {
		ModelAndView result;
		int uaId=LoginService.getPrincipal().getId();
		Collection<Actor> commentables = new ArrayList<Actor>();
		Actor actor = lessorService.findByUserAccountId(uaId);
		
		if(actor == null){
			actor = tenantService.findByUserAccountId(uaId);
			commentables.add(actor);
			commentables.addAll(lessorService.findMyLessors(actor.getId()));
			
		}else{
			commentables.add(actor);
			commentables.addAll(tenantService.findMyTenants(actor.getId()));
		}
		
		try {
			commentService.save(comment);
			result = new ModelAndView("redirect:list.do");
				
		} catch (Throwable oops) {
			result = new ModelAndView("comment/create");
			result.addObject("comment", comment);
			result.addObject("commentables", commentables);
			result.addObject("message", "comment.commit.error");
		}
		
		return result;
	}
	

}
