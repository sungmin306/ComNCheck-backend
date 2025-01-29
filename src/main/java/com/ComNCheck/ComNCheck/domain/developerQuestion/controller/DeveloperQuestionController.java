package com.ComNCheck.ComNCheck.domain.developerQuestion.controller;


import com.ComNCheck.ComNCheck.domain.developerQuestion.service.DeveloperQuestionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/developer/question")
@AllArgsConstructor
@RestController
public class DeveloperQuestionController {

    private final DeveloperQuestionService developerQuestionService;

    @PostMapping



}
