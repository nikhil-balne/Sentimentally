package com.main.sentimentally.repository;

import com.main.sentimentally.entity.Severity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeverityRepository extends JpaRepository<Severity, Long>{

}
