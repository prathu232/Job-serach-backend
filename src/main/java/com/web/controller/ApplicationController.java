package com.web.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.web.dto.ApplicationDto;
import com.web.model.Application;
import com.web.model.Job;
import com.web.repository.JobRepository;
import com.web.services.ApplicationService;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final JobRepository jobRepository;

    @Value("${resume.upload.dir}") // Load file upload directory from properties
    private String uploadDir;

    @Autowired
    public ApplicationController(ApplicationService applicationService, JobRepository jobRepository) {
        this.applicationService = applicationService;
        this.jobRepository = jobRepository;
    }

    /**
     * ✅ Apply for a job with resume upload
     */
    @PostMapping("/apply")
    public ResponseEntity<String> applyForJob(@RequestBody ApplicationDto applicationDto) { // ✅ Accept JSON
        try {
            // ✅ Fetch the Job entity using jobId
            Job job = jobRepository.findById(applicationDto.getJobId())
                    .orElseThrow(() -> new RuntimeException("Job not found with ID: " + applicationDto.getJobId()));

            // ✅ Create and save application object
            Application application = new Application();
            application.setJob(job);
            application.setApplicantName(applicationDto.getApplicantName());
            application.setEmail(applicationDto.getEmail()); // 🔍 Debug if email is null
            application.setPhoneNumber(applicationDto.getPhoneNumber());
            application.setResumePath(applicationDto.getResume()); // ✅ Store Firebase URL

            applicationService.saveApplication(application);

            return new ResponseEntity<>("Application submitted successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error saving application: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * ✅ Create an application without a job (Optional)
     */
    @PostMapping
    public ResponseEntity<Application> createApplication(
            @RequestParam("applicantName") String applicantName,
            @RequestParam("email") String email,
            @RequestParam("resume") MultipartFile resume) throws IOException {

        // ✅ Ensure the upload directory exists
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // ✅ Generate a unique filename and save file
        String uniqueFileName = System.currentTimeMillis() + "_" + resume.getOriginalFilename();
        Path filePath = uploadPath.resolve(uniqueFileName);
        resume.transferTo(filePath.toFile());

        // ✅ Save application details
        Application application = new Application();
        application.setApplicantName(applicantName);
        application.setEmail(email);
        application.setResumePath(filePath.toString());

        Application savedApplication = applicationService.saveApplication(application);
        return new ResponseEntity<>(savedApplication, HttpStatus.CREATED);
    }

    /**
     * ✅ Get application by ID
     */
    @GetMapping("/{applicationId}")
    public ResponseEntity<Application> getApplicationById(@PathVariable Long applicationId) {
        Application application = applicationService.getApplicationById(applicationId);
        return application != null ? new ResponseEntity<>(application, HttpStatus.OK)
                                   : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * ✅ Get applications by Job ID
     */
    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<Application>> getApplicationsByJobId(@PathVariable Long jobId) {
        List<Application> applications = applicationService.getApplicationsByJobId(jobId);
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    /**
     * ✅ Get applications by applicant name
     */
    @GetMapping("/applicant/{applicantName}")
    public ResponseEntity<List<Application>> getApplicationsByApplicantName(@PathVariable String applicantName) {
        List<Application> applications = applicationService.getApplicationsByApplicantName(applicantName);
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    /**
     * ✅ Update application details
     */
    @PutMapping("/{applicationId}")
    public ResponseEntity<Application> updateApplication(@PathVariable Long applicationId, 
                                                         @RequestBody Application application) {
        application.setApplicationId(applicationId);
        Application updatedApplication = applicationService.updateApplication(application);
        return updatedApplication != null ? new ResponseEntity<>(updatedApplication, HttpStatus.OK)
                                          : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * ✅ Delete application by ID
     */
    @DeleteMapping("/{applicationId}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long applicationId) {
        applicationService.deleteApplication(applicationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * ✅ Delete all applications for a specific job
     */
    @DeleteMapping("/job/{jobId}")
    public ResponseEntity<Void> deleteApplicationsByJobId(@PathVariable Long jobId) {
        applicationService.deleteApplicationsByJobId(jobId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
  
    

    /**
     * ✅ Global Exception Handling for Common Issues
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e) {
        return new ResponseEntity<>("File processing error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
