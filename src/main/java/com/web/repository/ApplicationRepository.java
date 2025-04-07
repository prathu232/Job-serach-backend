package com.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.web.model.Application;

import java.util.List;
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    // Find all applications for a specific job by jobId
    List<Application> findByJob_JobId(Long jobId);

    // Find all applications by applicant's name
    List<Application> findByApplicantName(String applicantName);

   
    // Find all applications by jobId and applicant name
    List<Application> findByJob_JobIdAndApplicantName(Long jobId, String applicantName);

    // Delete applications by jobId (useful for removing all applications related to a job)
    void deleteByJob_JobId(Long jobId);
    List<Application> findByJob_Employer_Id(Long employerId);
    
    List<Application> findByJob_JobIdIn(List<Long> jobIds);

}
