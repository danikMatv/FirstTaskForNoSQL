package org.example.taskonefornosql.Controller;

import org.example.taskonefornosql.Dto.SubscriptionRequest;
import org.example.taskonefornosql.Entity.User;
import org.example.taskonefornosql.Exception.UserNotFoundException;
import org.example.taskonefornosql.Node.UserNode;
import org.example.taskonefornosql.Repository.Neo4jRepository.Neo4jUserRepository;
import org.example.taskonefornosql.Service.UserService;
import org.example.taskonefornosql.Service.GraphService.UserGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final Neo4jUserRepository neo4jUserRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private UserGraphService userGraphService;

    public UserController(Neo4jUserRepository neo4jUserRepository) {
        this.neo4jUserRepository = neo4jUserRepository;
    }

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

    @PostMapping("/subscribe")
    public ResponseEntity<Void> subscribe(@RequestBody SubscriptionRequest request) {
        userGraphService.subscribe(request.getSubscriberId(), request.getSubscribedUserId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/unsubscribe")
    public ResponseEntity<Void> unsubscribe(@RequestBody SubscriptionRequest request) {
        userGraphService.unsubscribe(request.getSubscriberId(), request.getSubscribedUserId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{subscriberId}")
    public ResponseEntity<List<UserNode>> getSubscriptions(@PathVariable String subscriberId) {
        UserNode subscriber = neo4jUserRepository.findByUserId(subscriberId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + subscriberId));
        return ResponseEntity.ok(subscriber.getSubscriptions());
    }
}
