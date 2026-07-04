package com.example.demo.service;

import com.example.demo.dto.JobDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface JobService {

    List<JobDTO> getAllJobs();
    JobDTO getJobById(int id);
    JobDTO createJob(JobDTO jobDTO);
    JobDTO updateJob(int id, JobDTO jobDTO);
    JobDTO patchJob(int id, JobDTO partialJobDTO);
    void deleteJob(int id);

    List<JobDTO> getJobsByTitle(String title);
    List<JobDTO> getJobsByLocation(String location);
    List<JobDTO> getJobsByCompany(String company);
    List<JobDTO> getJobsByLocationAndSalary(String location, double minSalary);
    List<JobDTO> getJobsBySalaryRange(double min, double max);

    long countTotalJobs();
    long countJobsByLocation(String location);
    long countJobsByCompany(String company);
    long countJobsByTitle(String role);

    List<JobDTO> createJobsInBulk(List<JobDTO> jobDTOs);
    Page<JobDTO> getJobsWithPagination(Pageable pageable);
    void deleteJobsInBulk(List<Integer> ids);
    void clearAllJobs();
}