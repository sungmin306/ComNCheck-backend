package com.ComNCheck.ComNCheck.domain.majorQuestion.repository;

import com.ComNCheck.ComNCheck.domain.majorQuestion.model.entity.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByWriterMemberId(Long writerId);
}
