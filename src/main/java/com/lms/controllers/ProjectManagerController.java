package com.lms.controllers;

import com.lms.models.Commentaire;
import com.lms.models.Employee;
import com.lms.models.LeaveDetails;
import com.lms.models.Projet;
import com.lms.repository.ServiceRepository;
import com.lms.service.LeaveManageService;
import com.lms.service.ProjectManagerService;
import com.lms.service.ServiceManageService;
import com.lms.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProjectManagerController {
    @Autowired
    private ProjectManagerService projectManageService;
    @Autowired
    private LeaveManageService leavetManageService;
@Autowired
private ServiceManageService servicemanage;


    @Autowired

    ServiceRepository serviceRepository;

    @Autowired
    private UserInfoService userInfoService;
    @RequestMapping(value = "/user/apply-project", method = RequestMethod.GET)
    public ModelAndView applyProject(ModelAndView mav) {


        mav.addObject("ServiceList",  servicemanage.getAllActiveServices());
        mav.addObject("project", new Projet());

        mav.setViewName("createproject");
        return mav;
    }

    @RequestMapping(value = "/user/apply-project", method = RequestMethod.POST,consumes={"text/html","application/x-www-form-urlencoded"})
    public ModelAndView submitApplyLeave(ModelAndView mav, @Valid Projet projet,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            mav.setViewName("createproject"+projet.getService().getId());
        } else {
            Employee employee = userInfoService.getUserInfo();

            projet.setUsername(employee.getNom());
            projet.setId_manager(employee.getId());
            projectManageService.applyProject(projet);
            mav.addObject("successMessage", "Your Project is registered!");
            mav.setView(new RedirectView("/user/home"));
        }
        return mav;
    }




    @RequestMapping(value="/user/manage-projects",method= RequestMethod.GET)
    public ModelAndView manageProjects(ModelAndView mav) {
        Employee employee = userInfoService.getUserInfo();

        List<Projet> ProjectsList = projectManageService.getAllProjectsOfManager(employee.getId());
        mav.addObject("ProjectList", ProjectsList);
        mav.setViewName("manageProjects");
        return mav;
    }

    @RequestMapping(value="/user/all-projects",method= RequestMethod.GET)
    public ModelAndView allProjects(ModelAndView mav) {

        List<Projet> ProjectsList = projectManageService.getAllActiveProjects();
        mav.addObject("ProjectList", ProjectsList);
        mav.setViewName("manageProjects");
        return mav;
    }
    @RequestMapping(value="/user/show-project/{id}",method= RequestMethod.GET)
    public ModelAndView showMyProjects(ModelAndView mav, @PathVariable("id") int id) {
    //    LeaveDetails task = leaveManageService.getTaskById(id);
        List<LeaveDetails> leaveDetails;
        leaveDetails = leavetManageService.getAllLeavesOfProject(id);
        mav.addObject("tasksofProject", leaveDetails);


        mav.setViewName("TasksofProjects");
        return mav;
    }

    @RequestMapping(value = "/user/manage-projects/{action}/{id}", method = RequestMethod.GET)
    public ModelAndView acceptOrRejectLeaves(ModelAndView mav, @PathVariable("action") String action,
                                             @PathVariable("id") int id) {
        Projet projet = projectManageService.getProjectDetailsOnId(id);
        if (action.equals("accept")) {
            projet.setAcceptRejectFlag(true);
            projet.setActive(false);
        } else if (action.equals("reject")) {
            projet.setAcceptRejectFlag(false);
            projet.setActive(false);
        }
        projectManageService.updateLeaveDetails(projet);
        mav.addObject("successMessage", "Updated Successfully!");
        mav.addObject("etat",projet.isAcceptRejectFlag());
        mav.setView(new RedirectView("/user/manage-projects"));
        return mav;
    }


}
