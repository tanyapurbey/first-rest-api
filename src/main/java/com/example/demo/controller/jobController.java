package com.example.demo.controller;

import com.example.demo.dto.JobDTO;
import com.example.demo.service.JobService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;




import java.util.List;
@Validated
@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class jobController {

    private final JobService jobService;

//    public jobController(JobService jobService) {
//        this.jobService = jobService;
//    }

    // GET http://localhost:8080/api/jobs
    @GetMapping
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        List<JobDTO> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(jobs); // Returns 200 OK with the list of jobs
    }

    // GET http://localhost:8080/api/jobs/{id}
    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable int id) {
        JobDTO job = jobService.getJobById(id);
        return ResponseEntity.ok(job); // Returns 200 OK with the found job
    }

    //POST http://localhost:8080/api/jobs
    @PostMapping
    public ResponseEntity<JobDTO> createJob(@Valid @RequestBody JobDTO jobDTO) {
        JobDTO createdJob = jobService.createJob(jobDTO);
        return new ResponseEntity<>(createdJob, HttpStatus.CREATED); // Returns 201 Created status
    }

    //PUT http://localhost:8080/api/jobs/{id}
    @PutMapping("/{id}")
    public ResponseEntity<JobDTO> updateJob(@PathVariable int id,@Valid @RequestBody JobDTO jobDTO) {
        JobDTO updatedJob = jobService.updateJob(id, jobDTO);
        return ResponseEntity.ok(updatedJob); // Returns 200 OK with the updated data
    }

    // PATCH http://localhost:8080/api/jobs/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<JobDTO> patchJob(@PathVariable int id, @RequestBody JobDTO partialJobDTO) {
        JobDTO updatedJob = jobService.patchJob(id, partialJobDTO);
        return ResponseEntity.ok(updatedJob); // Returns 200 OK with the partially updated job data
    }


    //DELETE http://localhost:8080/api/jobs/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable int id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build(); // Returns 204 No Content (standard for successful deletes)
    }
// search for title
//    GET http://localhost:8080/api/jobs/search/title?title=Software Engineer
    @GetMapping("/search/title")
    public ResponseEntity<List<JobDTO>> searchByJobName(@RequestParam String title) {
        List<JobDTO> jobs = jobService.getJobsByTitle(title);
        return ResponseEntity.ok(jobs);
    }

//search for location
//    GET http://localhost:8080/api/jobs/search/location?location=New York
    @GetMapping("/search/location")
    public ResponseEntity<List<JobDTO>> searchByLocation(@RequestParam String location) {
        List<JobDTO> jobs = jobService.getJobsByLocation(location);
        return ResponseEntity.ok(jobs);
    }

//    search for comapny
    // GET http://localhost:8080/api/jobs/search/company?company=Google
    @GetMapping("/search/company")
    public ResponseEntity<List<JobDTO>> searchByCompany(@RequestParam String company) {
        List<JobDTO> jobs = jobService.getJobsByCompany(company);
        return ResponseEntity.ok(jobs);
    }


    //count total jobs
    // GET http://localhost:8080/api/jobs/count
    @GetMapping("/count")
    public ResponseEntity<Long> countTotalJobs() {
        long count = jobService.countTotalJobs();
        return ResponseEntity.ok(count);
    }

    //count job of certain area
    // GET http://localhost:8080/api/jobs/count/location?location=London
    @GetMapping("/count/location")
    public ResponseEntity<Long> countJobsByLocation(@RequestParam String location) {
        long count = jobService.countJobsByLocation(location);
        return ResponseEntity.ok(count);
    }

    //count jobs of specific company
    // GET http://localhost:8080/api/jobs/count/company?company=Apple
    @GetMapping("/count/company")
    public ResponseEntity<Long> countJobsByCompany(@RequestParam String company) {
        long count = jobService.countJobsByCompany(company);
        return ResponseEntity.ok(count);
    }

    //count jobs of specific role
    // GET http://localhost:8080/api/jobs/count/title?title=Developer
    @GetMapping("/count/title")
    public ResponseEntity<Long> countJobsByTitle(@RequestParam String title) {
        long count = jobService.countJobsByTitle(title);
        return ResponseEntity.ok(count);
    }

    //search by loaction &salary
    // GET http://localhost:8080/api/jobs/search/location-salary?location=Austin&minSalary=80000
    @GetMapping("/search/location-salary")
    public ResponseEntity<List<JobDTO>> searchByLocationAndSalary(
            @RequestParam String location,
            @RequestParam double minSalary) {
        List<JobDTO> jobs = jobService.getJobsByLocationAndSalary(location, minSalary);
        return ResponseEntity.ok(jobs);
    }

    // salary range filter
    // GET http://localhost:8080/api/jobs/filter/salary?min=50000&max=120000
    @GetMapping("/filter/salary")
    public ResponseEntity<List<JobDTO>> filterBySalaryRange(
            @RequestParam double min,
            @RequestParam double max) {
        List<JobDTO> jobs = jobService.getJobsBySalaryRange(min, max);
        return ResponseEntity.ok(jobs);
    }

    //bulk post jobs
    // POST http://localhost:8080/api/jobs/bulk
    @PostMapping("/bulk")
    public ResponseEntity<List<JobDTO>> createJobsInBulk(@RequestBody List<@Valid JobDTO> jobDTOs) {
        List<JobDTO> createdJobs = jobService.createJobsInBulk(jobDTOs);
        return new ResponseEntity<>(createdJobs, HttpStatus.CREATED);
    }

    //pagination and sorting
    // GET http://localhost:8080/api/jobs/page?page=0&size=10&sort=title,asc
    @GetMapping("/page")
    public ResponseEntity<Page<JobDTO>> getJobsWithPagination(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<JobDTO> jobPage = jobService.getJobsWithPagination(pageable);
        return ResponseEntity.ok(jobPage);
    }

//     bulk remove jobs
    //DELETE http://localhost:8080/api/jobs/bulk
    @DeleteMapping("/bulk")
    public ResponseEntity<Void> deleteJobsInBulk(@RequestBody List<Integer> ids) {
        jobService.deleteJobsInBulk(ids);
        return ResponseEntity.noContent().build(); // Returns 204 No Content
    }

//  bye bye all jobs
//    DELETE http://localhost:8080/api/jobs/clear-all
    @DeleteMapping("/clear-all")
    public ResponseEntity<Void> clearAllJobs() {
        jobService.clearAllJobs();
        return ResponseEntity.noContent().build(); // Returns 204 No Content
    }


}












