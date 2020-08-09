package engine.service;

import engine.quiz.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> getAllUsers();
    User saveUser(User user);
    User findUserById(int id);
    User findByEmail(String email);
    void deleteById(int id);
}
