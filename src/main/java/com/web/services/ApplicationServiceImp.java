package com.web.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.model.Application;
import com.web.repository.ApplicationRepository;

@Service
public class ApplicationServiceImp implements ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationServiceImp(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public Application saveApplication(Application application) {
        // Save the application and return the saved entity
        return applicationRepository.save(application);
    }

    @Override
    public Application getApplicationById(Long applicationId) {
        // Find the application by its applicationId
        return applicationRepository.findById(applicationId).orElse(null); // Or throw an exception if needed
    }

    @Override
    public List<Application> getApplicationsByJobId(Long jobId) {
        // Find all applications related to a specific jobId
        return applicationRepository.findByJob_JobId(jobId); // Assuming jobId is linked via Job entity
    }

    @Override
    public List<Application> getApplicationsByApplicantName(String applicantName) {
        // Find all applications by applicant name
        return applicationRepository.findByApplicantName(applicantName);
    }

    @Override
    public Application updateApplication(Application application) {
        Optional<Application> existingApplicationOpt = applicationRepository.findById(application.getApplicationId());

        if (existingApplicationOpt.isPresent()) {
            Application existingApplication = existingApplicationOpt.get();
            
            // Update only non-null fields
            if (application.getApplicantName() != null) {
                existingApplication.setApplicantName(application.getApplicantName());
            }
            if (application.getEmail() != null) {
                existingApplication.setEmail(application.getEmail());
            }
        
            if (application.getPhoneNumber() != null) {
                existingApplication.setPhoneNumber(application.getPhoneNumber());
            }
          
            if (application.getResumePath() != null) {
                existingApplication.setResumePath(application.getResumePath());
            }

            // Save and return the updated application
            return applicationRepository.save(existingApplication);
        }
        return null; // Return null if application doesn't exist
    }


    @Override
    public void deleteApplication(Long applicationId) {
        // Delete the application by its applicationId
        applicationRepository.deleteById(applicationId);
    }

    @Override
    public void deleteApplicationsByJobId(Long jobId) {
        // Delete all applications associated with a specific jobId
        List<Application> applications = applicationRepository.findByJob_JobId(jobId);
        if (applications != null && !applications.isEmpty()) {
            applicationRepository.deleteAll(applications);
        }
    }
    
    @Override
    public List<Application> getAllApplications() {
        return applicationRepository.findAll(); // Fetches all applications
    }
    
    public void deleteApplicationById(Long id) {
        applicationRepository.deleteById(id);
    }

}
