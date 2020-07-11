package engine.repository;

import engine.quiz.Question;

import java.util.List;

public interface QuizRepository {
    List<Question> getAllQuizzes();
}
