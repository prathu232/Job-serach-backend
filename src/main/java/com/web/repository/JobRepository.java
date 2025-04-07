package com.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.web.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
	  List<Job> findByJobType(String jobType);
	  List<Job> findByEmployer_Id(Long employerId);
	  
	  @Query("SELECT j FROM Job j LEFT JOIN FETCH j.applications WHERE j.employer.id = :employerId")
	  List<Job> findJobsWithApplicantsByEmployer(@Param("employerId") Long employerId);
	  List<Job> findByStatus(String status);
	  List<Job> findByJobTypeAndDegree(String jobType, String degree);
	  List<Job> findByDegree(String degree);
//	  List<Job> findByStatus(String status);

}
