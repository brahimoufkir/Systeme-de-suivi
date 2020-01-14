package com.lms.repository;

        import com.lms.models.LeaveDetails;
        import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveRepository extends JpaRepository<LeaveDetails, Integer> {

    LeaveDetails findById(int id);


}
