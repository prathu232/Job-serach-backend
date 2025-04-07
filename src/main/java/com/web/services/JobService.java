package com.web.services;

import java.util.List;

import com.web.model.Application;
import com.web.model.Job;

public interface JobService {
	 Job addJob(Job job);
	    List<Job> getAllJobs();
	    List<Job> getJobsByType(String jobType);
		//Object getJobsByEmployer(Long employerId);
	    List<Job> getJobsByEmployer(Long employerId);
	    List<Application>getApplicationForEmployer(Long employerId);
	   List<Job> getJobsWithApplicantsByEmployer(Long employerId) ;
	   public void deleteJobWithApplicants(Long jobId);
	   List<Job> findByStatus(String status);
	   void markJobAsInactive(Long jobId);
	   void markJobAsactive(Long jobId);
	Job getJobById(Long id);
	Job saveJob(Job job);
	List<Job> findByJobTypeAndDegree(String jobType, String degree);

	List<Job> findJobsByDegree(String degree);

	
}
