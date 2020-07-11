package engine.repository;

import engine.quiz.Question;

import java.util.List;

public class QuizReposiotryImpl implements QuizRepository {


    @Override
    @Transactional
    public List<Question> getAllQuizzes() {
        return null;
    }
}
