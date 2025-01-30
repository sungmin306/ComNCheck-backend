package com.ComNCheck.ComNCheck.domain.developerQuestion.repository;

import com.ComNCheck.ComNCheck.domain.developerQuestion.model.entity.DeveloperQuestion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;



public interface DeveloperQuestionRepository extends JpaRepository<DeveloperQuestion, Long> {

    //Optional<DeveloperQuestion> findByDeveloperQuestionId(Long id);


}
