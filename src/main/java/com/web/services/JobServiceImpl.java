package com.web.services;



import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.model.Application;
import com.web.model.Job;
import com.web.repository.ApplicationRepository;
import com.web.repository.JobRepository;

import jakarta.transaction.Transactional;
@Service

public  class JobServiceImpl implements JobService {


    @Autowired
    private JobRepository jobRepository;
    
    @Autowired
    private ApplicationRepository applicationRepository;
    
   
	
    @Transactional
	@Override
	public Job addJob(Job job) {
    	// System.out.println("✅ Before Saving Job: " + job);
         Job savedJob = jobRepository.save(job);
      //   System.out.println("✅ After Saving Job: " + savedJob);
         return savedJob;
	}
    
    public List<Job> getJobsByEmployer(Long employerId) {
        return jobRepository.findJobsWithApplicantsByEmployer(employerId);
    }



	@Override
	public List<Job> getAllJobs() {
		return jobRepository.findByStatus("Active");
	}

	@Override
	public List<Job> getJobsByType(String jobType) {
		  return jobRepository.findByJobType(jobType);
	}


	@Override
	public List<Application> getApplicationForEmployer(Long employerId) {
	    // Get all jobs posted by this employer
	    List<Job> jobs = jobRepository.findByEmployer_Id(employerId);

	    // If employer has no jobs, return an empty list
	    if (jobs.isEmpty()) {
	        return Collections.emptyList();
	    }

	    // Extract job IDs from job list
	    List<Long> jobIds = jobs.stream().map(Job::getJobId).toList();

	    // Fetch all applications for these jobs
	    List<Application> applications = applicationRepository.findByJob_JobIdIn(jobIds);

	    // Add job titles to the applications
	    applications.forEach(application -> {
	        Job job = application.getJob();
	        application.setJobTitle(job != null ? job.getJobRole() : "Job Not Found");
	    });

	    return applications;
	}


	@Override
	public List<Job> getJobsWithApplicantsByEmployer(Long employerId) {
	    List<Job> jobs = jobRepository.findJobsWithApplicantsByEmployer(employerId);
	    
	    // Check if jobs are found
	    if (jobs.isEmpty()) {
	        return Collections.emptyList();
	    }
	    
	    // ✅ Ensure applicants are initialized
	    jobs.forEach(job -> job.getApplications().size()); 

	    return jobs;
	}

	@Transactional
	public void deleteJobWithApplicants(Long jobId) {
	    applicationRepository.deleteByJob_JobId(jobId); // Deletes all applicants for the job
	    jobRepository.deleteById(jobId); // Deletes the job itself
	}

	@Override
	public List<Job> findByStatus(String status) {
		// TODO Auto-generated method stub
		return jobRepository.findByStatus(status);
	}

	@Override
	public void markJobAsInactive(Long jobId) {
		   Job job = jobRepository.findById(jobId)
		            .orElseThrow(() -> new RuntimeException("Job not found"));
		    
		    job.setStatus("Inactive");
		    jobRepository.save(job);
		
	}

	@Override
	public void markJobAsactive(Long jobId) {
		// TODO Auto-generated method stub
		
	}

	public Job getJobById(Long id) {
	    return jobRepository.findById(id).orElse(null);
	}

	public Job saveJob(Job job) {
	    return jobRepository.save(job);
	}

	@Override
	public List<Job> findByJobTypeAndDegree(String jobType, String degree) {
		// TODO Auto-generated method stub
		  return jobRepository.findByJobTypeAndDegree(jobType, degree);
	}

	@Override
	public List<Job> findJobsByDegree(String degree) {
		// TODO Auto-generated method stub
	    return jobRepository.findByDegree(degree);

	}





	
	 
}
