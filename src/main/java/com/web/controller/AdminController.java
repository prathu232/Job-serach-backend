package com.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.web.model.Admin;
import com.web.model.Application;
import com.web.model.Job;
import com.web.model.User;
import com.web.services.AdminServices;
import com.web.services.ApplicationService;
import com.web.services.JobService;
import com.web.services.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminServices adminService;
    private final ApplicationService applicationService;
    private final JobService jobService;
    private final UserService userService;

    public AdminController(AdminServices adminService, ApplicationService applicationService,
                           JobService jobService, UserService userService) {
        this.adminService = adminService;
        this.applicationService = applicationService;
        this.jobService = jobService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Admin admin) {
        String response = adminService.login(admin.getEmail(), admin.getPassword());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/jobs")
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/applications")
    public List<Application> getAllApplications() {
        return applicationService.getAllApplications();
    }

    @GetMapping("/employers")
    public List<User> getAllEmployers() {
        return userService.getAllEmployers();
    }

    // ✅ DELETE User
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    // ✅ DELETE Employer
    @DeleteMapping("/employers/{id}")
    public ResponseEntity<String> deleteEmployer(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("Employer deleted successfully");
    }

    // ✅ DELETE Job
    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        jobService.deleteJobWithApplicants(id);
        return ResponseEntity.ok("Job deleted successfully");
    }

    // ✅ DELETE Application
    @DeleteMapping("/applications/{id}")
    public ResponseEntity<String> deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.ok("Application deleted successfully");
    }
}
