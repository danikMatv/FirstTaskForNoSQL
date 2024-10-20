package org.example.taskonefornosql.Service;

import org.example.taskonefornosql.Entity.User;
import org.example.taskonefornosql.Repository.UserRepository;
import org.example.taskonefornosql.Service.GraphService.UserGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final UserGraphService userGraphService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserGraphService userGraphService) {
        this.userGraphService = userGraphService;
    }

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        userGraphService.createUserInGraph(savedUser); // Додати користувача в граф
        return savedUser;
    }

    public void deleteUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userGraphService.deleteUserInGraph(userId); // Видалити користувача з графа
        userRepository.delete(user);
    }

    public User addFriend(String userId, String friendId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new RuntimeException("Friend not found"));
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);

        userRepository.save(user);
        userRepository.save(friend);
        userGraphService.createFriendshipInGraph(userId, friendId); // Додати зв'язок в графі
        return user;
    }

    public void removeFriend(String userId, String friendId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new RuntimeException("Friend not found"));
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);

        userRepository.save(user);
        userRepository.save(friend);
        userGraphService.removeFriendshipInGraph(userId, friendId); // delete thread in graph
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
