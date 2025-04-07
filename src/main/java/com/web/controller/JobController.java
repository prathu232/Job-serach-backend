package com.web.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.model.Application;
import com.web.model.Job;
import com.web.services.JobService;

@RestController
@RequestMapping("/api/jobs")
public class JobController {


    @Autowired
    private JobService jobService;

    @PostMapping("/add")
    public ResponseEntity<Job> addJob(@RequestBody Job job) {
        Job savedJob = jobService.addJob(job);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedJob);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/filter")
    public List<Job> getFilteredJobs(
            @RequestParam(required = false) String jobType,
            @RequestParam(required = false) String degree) {
        
        if (jobType != null && degree != null) {
            return jobService.findByJobTypeAndDegree(jobType, degree);
        } else if (jobType != null) {
            return jobService.getJobsByType(jobType);
        } else if (degree != null) {
            return jobService.findJobsByDegree(degree);
        } else {
            return jobService.getAllJobs();
        }
    }

    
    @GetMapping("/employer/{employerId}")
    public ResponseEntity<List<Job>> getJobsByEmployer(@PathVariable Long employerId) {
        List<Job> jobs = jobService.getJobsByEmployer(employerId);
        return ResponseEntity.ok(jobs);
    }


    @GetMapping("/applicants/{employerId}")
    public ResponseEntity<List<Application>> getApplicantsForEmployer(@PathVariable Long employerId) {
        return ResponseEntity.ok(jobService.getApplicationForEmployer(employerId));
    }

    
    @DeleteMapping("/{jobId}")
    public ResponseEntity<String> deleteJob(@PathVariable Long jobId) {
        try {
            jobService.deleteJobWithApplicants(jobId);
            return ResponseEntity.ok("Job and corresponding applicants deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting job.");
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Job>> getJobsByStatus(@PathVariable String status) {
        List<Job> jobs = jobService.findByStatus(status);
        return ResponseEntity.ok(jobs);
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateJobStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        try {
            Job job = jobService.getJobById(id);
            if (job != null) {
                job.setStatus(status);
                jobService.saveJob(job);
                return ResponseEntity.ok("Job status updated to " + status);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update job status");
        }
    }




}
