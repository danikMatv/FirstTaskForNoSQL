package org.example.taskonefornosql.Controller;

import org.example.taskonefornosql.Entity.User;
import org.example.taskonefornosql.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.register(user);
    }

    @PutMapping("/{userId}/add-friend/{friendId}")
    public User addFriend(@PathVariable String userId, @PathVariable String friendId) {
        return userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<Object> removeFriend(@PathVariable String userId, @PathVariable String friendId) {
        userService.removeFriend(userId, friendId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public User searchUser(@RequestParam String email) {
        return userService.findByEmail(email);
    }
}

