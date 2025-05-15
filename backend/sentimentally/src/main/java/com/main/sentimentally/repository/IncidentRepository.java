package com.main.sentimentally.repository;

import com.main.sentimentally.entity.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {

	Optional<Incident> findByIdAndPropertyId(Long incidentId, String propertyId);

}
