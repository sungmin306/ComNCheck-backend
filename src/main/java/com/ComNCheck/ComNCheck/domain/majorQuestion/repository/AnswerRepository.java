package com.ComNCheck.ComNCheck.domain.majorQuestion.repository;

import com.ComNCheck.ComNCheck.domain.majorQuestion.model.entity.Answer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByQuestionId(Long questionId);
}
