package com.ComNCheck.ComNCheck.domain.majorQuestion.service;


import com.ComNCheck.ComNCheck.domain.Member.model.entity.Member;
import com.ComNCheck.ComNCheck.domain.Member.repository.MemberRepository;
import com.ComNCheck.ComNCheck.domain.global.exception.UnauthorizedException;
import com.ComNCheck.ComNCheck.domain.majorQuestion.model.dto.request.QuestionRequestDTO;
import com.ComNCheck.ComNCheck.domain.majorQuestion.model.dto.response.QuestionResponseDTO;
import com.ComNCheck.ComNCheck.domain.majorQuestion.model.entity.Question;
import com.ComNCheck.ComNCheck.domain.majorQuestion.repository.QuestionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public QuestionResponseDTO createQuestion(QuestionRequestDTO requestDTO) {
        Member writer = memberRepository.findById(requestDTO.getWriterId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        Question question = Question.builder()
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .writer(writer)
                .build();

        Question saveQuestion = questionRepository.save(question);
        return QuestionResponseDTO.of(saveQuestion);
    }

    public QuestionResponseDTO getQuestion(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 존재하지 않습니다."));
        return QuestionResponseDTO.of(question);
    }

    public List<QuestionResponseDTO> getAllQuestions() {
        return questionRepository.findAll()
                .stream()
                .map(QuestionResponseDTO::of)
                .toList();
    }

    @Transactional
    public QuestionResponseDTO updateQuestion(Long questionId, QuestionRequestDTO requestDTO, Long writerId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 존재하지 않습니다."));

        if (!question.getWriter().getId().equals(writerId)) {
            throw new UnauthorizedException("작성자가 아닙니다.");
        }

        question.updateQuestion(requestDTO.getTitle(), requestDTO.getContent());
        return QuestionResponseDTO.of(question);
    }

    @Transactional
    public void deleteQuestion(Long questionId, Long writerId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 존재하지 않습니다."));

        if(question.getWriter().getId().equals(writerId)) {
            throw new UnauthorizedException("작성자가 아닙니다.");
        }
        questionRepository.delete(question);
    }

    public List<QuestionResponseDTO> getMyQuestions(Long writerId) {
        return questionRepository.findAllByWriterId(writerId)
                .stream()
                .map(QuestionResponseDTO::of)
                .toList();
    }
}
