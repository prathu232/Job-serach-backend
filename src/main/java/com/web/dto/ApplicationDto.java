package com.web.dto;

public class ApplicationDto {

    private Long applicationId;
    private Long jobId;
    private String applicantName;
    private String email;         // ✅ Add email field
    private String phoneNumber;   // ✅ Add phone number field
    private String resume;
    private String status;
    private String jobName;

    // Default constructor
    public ApplicationDto() {
    }

    // Constructor with all fields
    public ApplicationDto(Long applicationId, Long jobId, String applicantName, String email, String phoneNumber, String resume, String status) {
        this.applicationId = applicationId;
        this.jobId = jobId;
        this.applicantName = applicantName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.resume = resume;
        this.status = status;
    }

    // Getters and Setters
    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getEmail() {  // ✅ Getter for email
        return email;
    }

    public void setEmail(String email) {  // ✅ Setter for email
        this.email = email;
    }

    public String getPhoneNumber() {  // ✅ Getter for phone number
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {  // ✅ Setter for phone number
        this.phoneNumber = phoneNumber;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
