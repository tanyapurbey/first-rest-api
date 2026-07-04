package com.example.demo.service.impl;

import com.example.demo.dto.JobDTO;
import com.example.demo.entity.JobEntry;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.JobRepositories;
import com.example.demo.service.JobService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepositories jobEntryRepository;
    private final ModelMapper modelMapper;

    public JobServiceImpl(JobRepositories jobEntryRepository, ModelMapper modelMapper) {
        this.jobEntryRepository = jobEntryRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<JobDTO> getAllJobs() {
        return jobEntryRepository.findAll().stream()
                .map(job -> modelMapper.map(job, JobDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public JobDTO getJobById(int id) {
        JobEntry job = jobEntryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with ID: " + id));
        return modelMapper.map(job, JobDTO.class);
    }

    @Override
    public JobDTO createJob(JobDTO jobDTO) {
        JobEntry newJob = modelMapper.map(jobDTO, JobEntry.class);
        JobEntry savedJob = jobEntryRepository.save(newJob);
        return modelMapper.map(savedJob, JobDTO.class);
    }

    @Override
    public JobDTO updateJob(int id, JobDTO jobDTO) {
        JobEntry existingJob = jobEntryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with ID: " + id));

        modelMapper.map(jobDTO, existingJob);

        JobEntry updatedJob = jobEntryRepository.save(existingJob);
        return modelMapper.map(updatedJob, JobDTO.class);
    }

    @Override
    public void deleteJob(int id) {
        if (!jobEntryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Job not found with ID: " + id);
        }
        jobEntryRepository.deleteById(id);
    }

    @Override
    public JobDTO patchJob(int id, JobDTO partialJobDTO) {
        JobEntry existingJob = jobEntryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with ID: " + id));

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(partialJobDTO, existingJob);
        modelMapper.getConfiguration().setSkipNullEnabled(false);

        JobEntry patchedJob = jobEntryRepository.save(existingJob);
        return modelMapper.map(patchedJob, JobDTO.class);
    }

    @Override
    public List<JobDTO> getJobsByTitle(String title) {
        return jobEntryRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(job -> modelMapper.map(job, JobDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<JobDTO> getJobsByLocation(String location) {
        return jobEntryRepository.findByLocationIgnoreCase(location).stream()
                .map(job -> modelMapper.map(job, JobDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<JobDTO> getJobsByCompany(String company) {
        return jobEntryRepository.findByCompanyIgnoreCase(company).stream()
                .map(job -> modelMapper.map(job, JobDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<JobDTO> getJobsByLocationAndSalary(String location, double minSalary) {
        return jobEntryRepository.findByLocationIgnoreCaseAndSalaryGreaterThanEqual(location, minSalary).stream()
                .map(job -> modelMapper.map(job, JobDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<JobDTO> getJobsBySalaryRange(double min, double max) {
        return jobEntryRepository.findBySalaryBetween(min, max).stream()
                .map(job -> modelMapper.map(job, JobDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public long countTotalJobs() {
        return jobEntryRepository.count();
    }

    @Override
    public long countJobsByLocation(String location) {
        return jobEntryRepository.countByLocationIgnoreCase(location);
    }

    @Override
    public long countJobsByCompany(String company) {
        return jobEntryRepository.countByCompanyIgnoreCase(company);
    }

    @Override
    public long countJobsByTitle(String title) {
        return jobEntryRepository.countByTitleIgnoreCase(title);
    }

    @Override
    @Transactional
    public List<JobDTO> createJobsInBulk(List<JobDTO> jobDTOs) {
        List<JobEntry> jobs = jobDTOs.stream()
                .map(dto -> modelMapper.map(dto, JobEntry.class))
                .collect(Collectors.toList());

        List<JobEntry> savedJobs = jobEntryRepository.saveAll(jobs);

        return savedJobs.stream()
                .map(job -> modelMapper.map(job, JobDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<JobDTO> getJobsWithPagination(Pageable pageable) {
        Page<JobEntry> jobPage = jobEntryRepository.findAll(pageable);
        return jobPage.map(job -> modelMapper.map(job, JobDTO.class));
    }

    @Override
    @Transactional
    public void deleteJobsInBulk(List<Integer> ids) {
        jobEntryRepository.deleteAllByIdInBatch(ids);
    }

    @Override
    @Transactional
    public void clearAllJobs() {
        jobEntryRepository.deleteAllInBatch();
    }
}