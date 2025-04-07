package com.web.model;

import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "company_name", nullable = true)
    private String companyName;

    @Column(name = "jobRole", nullable = true)
    private String jobRole;

    @Column(name = "job_shift", nullable = true)
    private String jobShift;

    @Column(name = "trade", nullable = true)
    private String trade;

    @Column(name = "degree", nullable = true)
    private String degree;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "status", nullable = true)
    private String status;

    @Column(name = "charges", nullable = true)
    private String charges;

    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = true)
    private Date date;

    @Column(name = "state", nullable = true)
    private String state;

    @Column(name = "city", nullable = true)
    private String city;

    @Column(name = "location", nullable = true)
    private String location;

    @Column(name = "job_type", nullable = true)
    private String jobType;

    @Column(name = "salary", nullable = true)
    private String salary;

    @Column(name = "experience", nullable = true)
    private String experience;

    @Column(name = "gender", nullable = true)
    private String gender;

    @Column(name = "mobile_number", nullable = true)
    private Long mobileNumber;

    @ManyToOne
    @JoinColumn(name = "employer_id", referencedColumnName = "id")
    private User employer;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Application> applications;

    @PrePersist
    protected void onCreate() {
        if (!StringUtils.hasText(status)) {
            status = "Active";
        }
    }

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getJobRole() {
		return jobRole;
	}

	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}

	public String getJobShift() {
		return jobShift;
	}

	public void setJobShift(String jobShift) {
		this.jobShift = jobShift;
	}

	public String getTrade() {
		return trade;
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCharges() {
		return charges;
	}

	public void setCharges(String charges) {
		this.charges = charges;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public User getEmployer() {
		return employer;
	}

	public void setEmployer(User employer) {
		this.employer = employer;
	}

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}
    
    
    
}
