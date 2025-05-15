package com.main.sentimentally.repository;

import com.main.sentimentally.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query(value = """
        SELECT af.* 
        FROM analysed_feedback af
        JOIN property p ON af.property_id = p.id
        WHERE (:propertyId IS NULL OR af.property_id = :propertyId)
          AND (:categoryId IS NULL OR :categoryId = ANY(af.category_ids))
          AND (:brandId IS NULL OR p.brand_id = :brandId)
          AND (:state IS NULL OR LOWER(p.state) = LOWER(:state))
          AND af.created_tsz >= :startDate
        """, nativeQuery = true)
    List<Feedback> findFeedbackByCategoryIdAndPropertyId(Integer categoryId, String propertyId, String state, String brandId, OffsetDateTime startDate);
}
