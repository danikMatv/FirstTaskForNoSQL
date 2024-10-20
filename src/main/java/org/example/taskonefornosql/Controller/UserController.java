package org.example.taskonefornosql.Controller;

import org.example.taskonefornosql.Entity.User;
import org.example.taskonefornosql.Service.UserService;
import org.example.taskonefornosql.Service.GraphService.UserGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserGraphService userGraphService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        User newUser = userService.register(user);
        userGraphService.createUserInGraph(newUser); // add user to graph
        return newUser;
    }

    @PutMapping("/{userId}/add-friend/{friendId}")
    public User addFriend(@PathVariable String userId, @PathVariable String friendId) {
        User updatedUser = userService.addFriend(userId, friendId);
        userGraphService.createFriendshipInGraph(userId, friendId); // add thread to graph
        return updatedUser;
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<Object> removeFriend(@PathVariable String userId, @PathVariable String friendId) {
        userService.removeFriend(userId, friendId);
        userGraphService.removeFriendshipInGraph(userId, friendId); // delete thread from graph
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> removeUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        userGraphService.deleteUserInGraph(userId); // delete user from graph
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public User searchUser(@RequestParam String email) {
        return userService.findByEmail(email);
    }
}
