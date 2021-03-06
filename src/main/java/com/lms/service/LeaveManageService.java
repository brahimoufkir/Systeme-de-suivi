package com.lms.service;

import com.lms.models.Commentaire;
import com.lms.models.LeaveDetails;
import com.lms.repository.LeaveManageNativeSqlRepo;
import com.lms.repository.LeaveManageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "leaveManageService")
public class LeaveManageService {

    @Autowired
    private LeaveManageRepository leaveManageRepository;

    @Autowired
    private LeaveManageNativeSqlRepo leaveManageNativeRepo;

    @SuppressWarnings("deprecation")
    public void applyLeave(LeaveDetails leaveDetails) {

	int duration = leaveDetails.getToDate().getDate() - leaveDetails.getFromDate().getDate();

	    leaveDetails.setDuration(duration + 1);
	    leaveDetails.setActive(true);
		leaveManageRepository.save(leaveDetails);
    }

    public List<LeaveDetails> getAllLeaves() {

	return leaveManageRepository.findAll();
    }
    public LeaveDetails getTaskById(int id) {

        return leaveManageRepository.findById(id);}

    public LeaveDetails getLeaveDetailsOnId(int id) {

	return leaveManageRepository.findOne(id);
    }

    public void updateLeaveDetails(LeaveDetails leaveDetails) {

	leaveManageRepository.save(leaveDetails);

    }

    public List<LeaveDetails> getAllActiveLeaves() {

	return leaveManageRepository.getAllActiveLeaves();
    }



    public List<LeaveDetails> getAllLeavesOfUser(String username) {

	return leaveManageRepository.getAllLeavesOfUser(username);

    }
    public List<LeaveDetails> getAllLeavesOfManager(int id_user_manager) {

        return leaveManageRepository.getAllLeavesOfManager(id_user_manager);

    }
    public List<LeaveDetails> getAllLeavesOfProject(int id_project) {

        return leaveManageRepository.getAllLeavesOfProject(id_project);

    }
    public List<LeaveDetails> getAllLeavesOfManager1(int id_user_manager,int id_user) {

        return leaveManageRepository.getAllLeavesOfManager1(id_user_manager,id_user);

    }

    public List<LeaveDetails> getAllLeavesOnStatus(boolean pending, boolean accepted, boolean rejected) {

	StringBuffer whereQuery = new StringBuffer();
	if (pending)
	    whereQuery.append("active=true or ");
	if (accepted)
	    whereQuery.append("(active=false and accept_reject_flag=true) or ");
	if (rejected)
	    whereQuery.append("(active=false and accept_reject_flag=false) or ");

	whereQuery.append(" 1=0");
	
	return leaveManageNativeRepo.getAllLeavesOnStatus(whereQuery);
    }


}
