package com.lms.controllers;

import com.lms.models.Commentaire;
import com.lms.models.Employee;
import com.lms.models.LeaveDetails;
import com.lms.models.Projet;
import com.lms.repository.LeaveManageRepository;
import com.lms.service.CommentaireService;
import com.lms.service.LeaveManageService;
import com.lms.service.ProjectManagerService;
import com.lms.service.UserInfoService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class LeaveManageController {

    @Autowired
    private LeaveManageService leaveManageService;

    @Autowired
    private UserInfoService userInfoService;
	@Autowired
	private ProjectManagerService projectManagerService;
@Autowired
private CommentaireService commentaireService;
	@Autowired
	private LeaveManageRepository leaveManageRepository;
    @RequestMapping(value = "/user/create-task", method = RequestMethod.GET)
    public ModelAndView applyLeave(ModelAndView mav) {
		mav.addObject("ProjectList",  projectManagerService.getAllActiveProjects());

	mav.addObject("leaveDetails", new LeaveDetails());
	mav.setViewName("CreateTask");
	return mav;
    }

   /* @RequestMapping(value = "/user/apply-leave", method = RequestMethod.POST)
    public ModelAndView submitApplyLeave(ModelAndView mav, @Valid LeaveDetails leaveDetails,
                                         BindingResult bindingResult) {

	Employee employee = userInfoService.getUserInfo();
	if (bindingResult.hasErrors()) {
	    mav.setViewName("applyLeave");
	} else {
	    leaveDetails.setUsername(employee.getEmail());
	    leaveDetails.setEmployeeName(employee.getNom() + " " + employee.getPrenom());
	    leaveDetails.setEmployee_id_manager(leaveDetails.getProjet().getId_manager());
		System.out.println("leaave " + leaveDetails.getEmployeeName() +  " " +leaveDetails.getLeaveType() + " " + leaveDetails.getReason()+" "+leaveDetails.getFromDate()+" "+leaveDetails.getToDate()+ " "+leaveDetails.isActive()+ " "+ leaveDetails.getUsername()+ " "+leaveDetails.getDuration()+" "+leaveDetails.isAcceptRejectFlag());
		leaveManageService.applyLeave(leaveDetails);
	    mav.addObject("successMessage", "Your Leave Request is registered!");
	    mav.setView(new RedirectView("/user/home"));
	}
	return mav;
    }*/
	@RequestMapping(value = "/user/create-task", method = RequestMethod.POST)
	public ModelAndView submitApplyLeave(ModelAndView mav, @Valid LeaveDetails leaveDetails, BindingResult bindingResult) {

		Employee employee = userInfoService.getUserInfo();
		if (bindingResult.hasErrors()) {
			mav.setViewName("CreateTask");
		} else {
			Projet projet = projectManagerService.getProjetById(leaveDetails.getProjet().getId());
			leaveDetails.setProjet(projet);
			leaveDetails.setCompleted("0");
			leaveDetails.setUsername(employee.getEmail());
			leaveDetails.setEmployeeName(employee.getNom() + " " + employee.getPrenom());
			leaveDetails.setEmployee_id_manager(leaveDetails.getProjet().getId_manager());
			leaveDetails.setEmployee_id(employee.getId());

			Commentaire commentaire = new Commentaire(leaveDetails.getLeaveType(),leaveDetails.getReason(),employee,leaveDetails);
			Set<Commentaire> commentaires = new HashSet<Commentaire>();
			commentaires.add(commentaire);
			leaveDetails.setCommentaires(commentaires);
			leaveManageService.applyLeave(leaveDetails);
			commentaireService.saveCommentaire(commentaire);
			mav.addObject("successMessage", "Your Task is registered!");
			mav.setView(new RedirectView("/user/home"));
		}
		return mav;
	}


	@RequestMapping(value = "/user/get-all-leaves", method = RequestMethod.GET)
    public @ResponseBody
    String getAllLeaves(@RequestParam(value = "pending", defaultValue = "false") boolean pending,
                        @RequestParam(value = "accepted", defaultValue = "false") boolean accepted,
                        @RequestParam(value = "rejected", defaultValue = "false") boolean rejected) throws Exception {
		Employee employee = userInfoService.getUserInfo();

		List<LeaveDetails> leavesList = leaveManageService.getAllLeavesOfManager1(employee.getId(),employee.getId());
	Iterator<LeaveDetails> iterator = leavesList.iterator();
	if (pending || accepted || rejected)
	    iterator = leaveManageService.getAllLeavesOnStatus(pending, accepted, rejected).iterator();
	JSONArray jsonArr = new JSONArray();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Calendar calendar = Calendar.getInstance();

	while (iterator.hasNext()) {
	    LeaveDetails leaveDetails = iterator.next();
	    calendar.setTime(leaveDetails.getToDate());
	    calendar.add(Calendar.DATE, 1);

	    JSONObject jsonObj = new JSONObject();
	    jsonObj.put("title", leaveDetails.getEmployeeName());
	    jsonObj.put("start", dateFormat.format(leaveDetails.getFromDate()));
	    jsonObj.put("end", dateFormat.format(calendar.getTime()));
	    if (leaveDetails.isActive())
		jsonObj.put("color", "#0878af");
	    if (!leaveDetails.isActive() && leaveDetails.isAcceptRejectFlag())
		jsonObj.put("color", "green");
	    if (!leaveDetails.isActive() && !leaveDetails.isAcceptRejectFlag())
		jsonObj.put("color", "red");
	    jsonArr.put(jsonObj);
	}

	return jsonArr.toString();
    }

	@RequestMapping(value = "/user/tache-edit/{id}", method = RequestMethod.GET)
	public ModelAndView editTaches(ModelAndView mav,
								   @PathVariable("id") int id) {


		//String iscomplete = "true";
		LeaveDetails tache = new LeaveDetails();
		tache = leaveManageService.getTaskById(id);
//		Role iscomplete = new Role();

//		mav.addObject("userTache",tache);
//		mav.addObject("isc	omplete",iscomplete);
////		mav.addObject("idtache",idtache);

		mav.addObject("userTache", tache);
		mav.setViewName("tacheEdit");
		return mav;
	}




	@RequestMapping(value = "/user/tache-edit/save", method = RequestMethod.POST)
	public ModelAndView saveUserEdit(ModelAndView mav,@Valid LeaveDetails tache, BindingResult bindResult) {



		//System.out.println("tist"+tache.getCompleted()+tache.getId());
		LeaveDetails tacheupdate = leaveManageService.getTaskById(tache.getId());
		if(tache.getCompleted().equals("1"))
		{
			tacheupdate.setCompleted("1");
		}
		else tacheupdate.setCompleted("0");

		leaveManageRepository.save(tacheupdate);

		mav.setView(new RedirectView("../my-tasks"));
		return mav;
	}


	@RequestMapping(value = "/user/get-admin-all-leaves", method = RequestMethod.GET)
	public @ResponseBody
	String getAdminAllLeaves(@RequestParam(value = "pending", defaultValue = "false") boolean pending,
						@RequestParam(value = "accepted", defaultValue = "false") boolean accepted,
						@RequestParam(value = "rejected", defaultValue = "false") boolean rejected) throws Exception {

		List<LeaveDetails> leavesList = leaveManageService.getAllLeaves();
		Iterator<LeaveDetails> iterator = leavesList.iterator();
		if (pending || accepted || rejected)
			iterator = leaveManageService.getAllLeavesOnStatus(pending, accepted, rejected).iterator();
		JSONArray jsonArr = new JSONArray();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();

		while (iterator.hasNext()) {
			LeaveDetails leaveDetails = iterator.next();
			calendar.setTime(leaveDetails.getToDate());
			calendar.add(Calendar.DATE, 1);

			JSONObject jsonObj = new JSONObject();
			jsonObj.put("title", leaveDetails.getEmployeeName());
			jsonObj.put("start", dateFormat.format(leaveDetails.getFromDate()));
			jsonObj.put("end", dateFormat.format(calendar.getTime()));
			if (leaveDetails.isActive())
				jsonObj.put("color", "#0878af");
			if (!leaveDetails.isActive() && leaveDetails.isAcceptRejectFlag())
				jsonObj.put("color", "green");
			if (!leaveDetails.isActive() && !leaveDetails.isAcceptRejectFlag())
				jsonObj.put("color", "red");
			jsonArr.put(jsonObj);
		}

		return jsonArr.toString();
	}
    
    @RequestMapping(value="/user/manage-tasks",method= RequestMethod.GET)
    public ModelAndView manageLeaves(ModelAndView mav) {
		Employee employee = userInfoService.getUserInfo();

		List<LeaveDetails> leavesList = leaveManageService.getAllLeavesOfManager(employee.getId());

	mav.addObject("leavesList", leavesList);
	mav.setViewName("manageTasks");
	return mav;
    }

	@RequestMapping(value="/user/all-leaves",method= RequestMethod.GET)
	public ModelAndView allLeaves(ModelAndView mav) {

		List<LeaveDetails> leavesList = leaveManageService.getAllLeaves();

		mav.addObject("leavesList", leavesList);
		mav.setViewName("manageTasks");
		return mav;
	}

    @RequestMapping(value = "/user/manage-tasks/{action}/{id}", method = RequestMethod.GET)
    public ModelAndView acceptOrRejectLeaves(ModelAndView mav, @PathVariable("action") String action,
                                             @PathVariable("id") int id) {
	LeaveDetails leaveDetails = leaveManageService.getLeaveDetailsOnId(id);
	if (action.equals("accept")) {
	    leaveDetails.setAcceptRejectFlag(true);
	    leaveDetails.setActive(false);
	} else if (action.equals("reject")) {
	    leaveDetails.setAcceptRejectFlag(false);
	    leaveDetails.setActive(false);
	}
	leaveManageService.updateLeaveDetails(leaveDetails);
	mav.addObject("successMessage", "Updated Successfully!");
	mav.addObject("etat",leaveDetails.isAcceptRejectFlag());
	mav.setView(new RedirectView("/user/manage-tasks"));
	return mav;
    }

    @RequestMapping(value = "/user/my-tasks", method = RequestMethod.GET)
    public ModelAndView showMyLeaves(ModelAndView mav) {

	Employee employee = userInfoService.getUserInfo();
	List<LeaveDetails> leavesList = leaveManageService.getAllLeavesOfUser(employee.getEmail());
	mav.addObject("leavesList", leavesList);
	mav.setViewName("myTasks");
	return mav;
    }
}
