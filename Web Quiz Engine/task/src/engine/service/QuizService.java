package engine.service;

import engine.quiz.Question;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuizService {
    List<Question> getAllQuestions();
    void saveQuestion(Question question);
    Question findQuestionById(int id);
}
