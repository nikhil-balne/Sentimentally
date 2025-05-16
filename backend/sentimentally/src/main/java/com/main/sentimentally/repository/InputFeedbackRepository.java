package com.main.sentimentally.repository;

import com.main.sentimentally.entity.InputFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InputFeedbackRepository extends JpaRepository<InputFeedback, Long> {

}
