package com.example.demo.repository;

import com.example.demo.entity.JobEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepositories extends JpaRepository<JobEntry ,Integer>{

    List<JobEntry> findByTitleContainingIgnoreCase(String title);

    List<JobEntry> findByLocationIgnoreCase(String location);

    List<JobEntry> findByCompanyIgnoreCase(String company);

    List<JobEntry> findByLocationIgnoreCaseAndSalaryGreaterThanEqual(String location, double salary);

    List<JobEntry> findBySalaryBetween(double min, double max);

    long countByLocationIgnoreCase(String location);

    long countByCompanyIgnoreCase(String company);

    long countByTitleIgnoreCase(String role);
}
