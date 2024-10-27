package fr.iutvannes.info.but3.userservice.service;

import fr.iutvannes.info.but3.userservice.entity.User;
import fr.iutvannes.info.but3.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private void simulateSlowService() {
        if (Math.random() < 0.5) { // 50% des requêtes seront ralenties
            try {
                Thread.sleep(61000); // Pause de 61 secondes pour forcer le timeout
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        simulateSlowService();
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        simulateSlowService();
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user) {
        simulateSlowService();
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        simulateSlowService();
        user.setId(id);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        simulateSlowService();
        userRepository.deleteById(id);
    }
}
