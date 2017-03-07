package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CommentRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Comment;
import domain.Commentable;

@Service
@Transactional
public class CommentService {

	//Managed repository
	@Autowired
	private CommentRepository commentRepository;
	
	//Supporting services
	@Autowired
	private LessorService lessorService;
	
	@Autowired
	private TenantService tenantService;
	
	@Autowired
	private Validator validator;
	
	//Constructors
	public CommentService() {
		super();
	}
	
	//Simple CRUD methods
	public Comment create(Actor sender, Commentable commentable) {
		Assert.notNull(sender, "The sender cannot be null");
		Assert.notNull(commentable, "The recipient cannot be null");
		Comment res;
		res = new Comment();
		res.setSender(sender);
		res.setCommentable(commentable);
		res.setMoment(Calendar.getInstance().getTime());
		res.setStar(1);
		
		return res;
	}
	
	public Collection<Comment> findAll() {
		Collection<Comment> res = commentRepository.findAll();
		return res;
	}

	public Comment findOne(int commentId) {
		Comment res = commentRepository.findOne(commentId);
		return res;
	}
	
	public Comment save(Comment comment) {
		Assert.notNull(comment, "The comment to save cannot be null.");
		Assert.isTrue(comment.getStar()>0&&comment.getStar()<=5,"The stars must be between 1 and 5");
		Comment res = commentRepository.save(comment);
		res.setMoment(Calendar.getInstance().getTime());
		
		return res;
	}
	
	public void delete(Comment comment) {
		Assert.notNull(comment, "The comment to delete cannot be null.");
		Assert.isTrue(commentRepository.exists(comment.getId()));
		
		Authority b = new Authority();
		b.setAuthority(Authority.ADMIN);

		UserAccount ua=LoginService.getPrincipal();
		Assert.isTrue(comment.getSender().getUserAccount().equals(ua) || ua.getAuthorities().contains(b), "You are not the owner of the comment");
		
		commentRepository.delete(comment);
	}
	
	
	//Utilites methods
	public Collection<Comment> findMyComments(){
		Authority b = new Authority();
		Authority c = new Authority();
		b.setAuthority(Authority.LESSOR);
		c.setAuthority(Authority.TENANT);
		UserAccount ua=LoginService.getPrincipal();
		Assert.isTrue(ua.getAuthorities().contains(b) || ua.getAuthorities().contains(c) , "You must to be a lessor or a tenant for this action");
		
		return commentRepository.findMyComments(ua.getId());
	}
	
	public Collection<Comment> findProfileComments(int actorId){
		Authority b = new Authority();
		Authority c = new Authority();
		b.setAuthority(Authority.LESSOR);
		c.setAuthority(Authority.TENANT);
		UserAccount ua=LoginService.getPrincipal();
		Assert.isTrue(ua.getAuthorities().contains(b) || ua.getAuthorities().contains(c) , "You must to be a lessor or a tenant for this action");
		
		return commentRepository.findProfileComments(actorId);
	}

	public Comment reconstruct(Comment comment, BindingResult binding) {
		Comment res;
		int uaId=LoginService.getPrincipal().getId();
		Actor actor = lessorService.findByUserAccountId(uaId);
		if(actor == null){
			actor = tenantService.findByUserAccountId(uaId);
		}
		
		res = this.create(actor, comment.getCommentable());
		
		res.setTitle(comment.getTitle());
		res.setText(comment.getText());
//		if(comment.getStar()==0 || comment.getStar()<0 || comment.getStar()>5){
//			res.setStar(1);
//		}else{
			res.setStar(comment.getStar());
//		}
		
		validator.validate(res, binding);
		
		return res;
	}

}

