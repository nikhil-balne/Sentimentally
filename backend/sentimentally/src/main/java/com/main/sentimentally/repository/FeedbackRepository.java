package com.main.sentimentally.repository;

import com.main.sentimentally.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query(value = "SELECT * \n" +
            "FROM analysed_feedback af\n" +
            "WHERE af.property_id = :propertyId \n" +
            "  AND :categoryId = ANY(af.category_ids);", nativeQuery = true)
    List<Feedback> findFeedbackByCategoryIdAndPropertyId(Integer categoryId, String propertyId);
}
