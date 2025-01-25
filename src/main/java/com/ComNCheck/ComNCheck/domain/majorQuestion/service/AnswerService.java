package com.ComNCheck.ComNCheck.domain.majorQuestion.service;

import com.ComNCheck.ComNCheck.domain.Member.model.entity.Member;
import com.ComNCheck.ComNCheck.domain.Member.repository.MemberRepository;
import com.ComNCheck.ComNCheck.domain.majorQuestion.model.dto.request.AnswerRequestDTO;
import com.ComNCheck.ComNCheck.domain.majorQuestion.model.dto.response.AnswerResponseDTO;
import com.ComNCheck.ComNCheck.domain.majorQuestion.model.entity.Answer;
import com.ComNCheck.ComNCheck.domain.majorQuestion.model.entity.Question;
import com.ComNCheck.ComNCheck.domain.majorQuestion.repository.AnswerRepository;
import com.ComNCheck.ComNCheck.domain.majorQuestion.repository.QuestionRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public AnswerResponseDTO createOrUpdateAnswer(AnswerRequestDTO requestDTO) {
        // Role 체크 로직
        Member writer = memberRepository.findById(requestDTO.getWriterId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        Question question = questionRepository.findById(requestDTO.getWriterId())
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 존재하지 않습니다."));

        Answer answer = answerRepository.findByQuestionId(question.getId()).orElse(null);

        if (answer != null) {
            answer.updateAnswer(requestDTO.getContent());
            return AnswerResponseDTO.of(answer);
        } else {
            Answer newAnswer = Answer.builder()
                    .content(requestDTO.getContent())
                    .question(question)
                    .writer(writer)
                    .build();

            Answer saveAnswer = answerRepository.save(newAnswer);
            question.setAnswer(saveAnswer);
            return AnswerResponseDTO.of(saveAnswer);
        }
    }
    @Transactional
    public AnswerResponseDTO updateAnswer(Long answerId, String content) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 답글이 존재하지 않습니다."));

        answer.updateAnswer(content);
        return AnswerResponseDTO.of(answer);
    }

    @Transactional
    public void deleteAnswer(Long answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 답글이 존재하지 않습니다."));
        answerRepository.delete(answer);
}

}
