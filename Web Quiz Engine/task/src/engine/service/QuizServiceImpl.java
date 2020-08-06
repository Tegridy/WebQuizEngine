package engine.service;

import engine.quiz.Question;
import engine.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class QuizServiceImpl implements QuizService{

    QuizRepository quizRepository;

    @Autowired
    public QuizServiceImpl(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public QuizServiceImpl() {
    }

    @Override
    @Transactional
    public List<Question> getAllQuestions() {
        return quizRepository.findAll();
    }

    @Override
    @Transactional
    public void saveQuestion(Question question) {
        quizRepository.save(question);
    }

    @Override
    @Transactional
    public Question findQuestionById(int id) {
        Optional<Question> question = quizRepository.findById(id);
        return question.orElse(null);
    }


}
