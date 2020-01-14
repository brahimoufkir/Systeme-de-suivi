package com.lms.controllers;

import com.lms.models.Commentaire;
import com.lms.models.Employee;
import com.lms.models.LeaveDetails;
import com.lms.repository.LeaveRepository;
import com.lms.service.CommentaireService;
import com.lms.service.LeaveManageService;
import com.lms.service.UserInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CommentaireController {

	@Autowired
	CommentaireService commentaireService;

	@Autowired
	LeaveManageService leaveManageService;

	@Autowired
	LeaveRepository leaveRepository;

	@Autowired
	UserInfoService userInfoService;

	static final Logger logger = Logger.getLogger(CommentaireController.class);
String type_comment;
	@GetMapping("/user/show-task/{id}")
	public ModelAndView showMyLeaves(ModelAndView mav, @PathVariable("id") int id) {
		LeaveDetails task = leaveManageService.getTaskById(id);
		List<Commentaire> CommentaireList;
		CommentaireList = commentaireService.getTaskComments(id);
		int size = CommentaireList.size();
		mav.addObject("task", task);
		mav.addObject("size", ""+size);
		mav.addObject("comment", new Commentaire());
		mav.addObject("comments", CommentaireList);
		mav.setViewName("Taskdetails");
		return mav;
	}

	/*	@PostMapping("/user/show-task/{id}")
        public String create(ModelAndView mav, @PathVariable("id") int task_id , @ModelAttribute(value ="comment") Commentaire commentaire){
            LeaveDetails task = leaveManageService.getLeaveDetailsOnId(task_id);
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Employee employee = userInfoService.findUserByEmail(username);
            String message = commentaire.getMessage();
            commentaire = new Commentaire("QUOTIDIEN",message,employee,task);
            commentaireService.saveCommentaire(commentaire);
            return "manageLeaves";
        }*/
	@PostMapping("/user/show-task/{id}")
	public String create(ModelAndView mav, @PathVariable("id") int task_id , @ModelAttribute(value ="comment") Commentaire commentaire){
		LeaveDetails task = leaveManageService.getLeaveDetailsOnId(task_id);
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Employee employee = userInfoService.findUserByEmail(username);
		String message = commentaire.getMessage();
		String type = commentaire.getType();
		if(type.equals("0"))
		{
			type_comment = "QUOTIDIEN";
		}
		else if (type.equals("1"))
		{
			type_comment = "INFORMATIF";
		}
		else
		{
			type_comment = "URGENT";
		}
		commentaire = new Commentaire(type_comment,message,employee,task);
		commentaireService.saveCommentaire(commentaire);
		return "redirect:/user/show-task/{id}";
	}

}
