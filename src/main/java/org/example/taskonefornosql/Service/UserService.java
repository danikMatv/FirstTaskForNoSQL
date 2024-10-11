package org.example.taskonefornosql.Service;

import org.example.taskonefornosql.Entity.User;
import org.example.taskonefornosql.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
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
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}

