package com.web.services;

import com.web.model.Application;
import java.util.List;

public interface ApplicationService {

    // Save a new application
    Application saveApplication(Application application);

    // Find an application by its applicationId
    Application getApplicationById(Long applicationId);

    // Find all applications by jobId
    List<Application> getApplicationsByJobId(Long jobId);

    // Find all applications by applicant name
    List<Application> getApplicationsByApplicantName(String applicantName);

    // Update an application (status, etc.)
    Application updateApplication(Application application);

    // Delete an application by its applicationId
    void deleteApplication(Long applicationId);

    // Delete all applications for a specific job
    void deleteApplicationsByJobId(Long jobId);
    
    List<Application> getAllApplications(); // New method to fetch all applications
}
