package org.example.taskonefornosql.Service.GraphService;

import org.example.taskonefornosql.Entity.User;
import org.example.taskonefornosql.Exception.UserNotFoundException;
import org.example.taskonefornosql.Node.UserNode;
import org.example.taskonefornosql.Repository.Neo4jRepository.Neo4jUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserGraphService {
    private static final Logger logger = LoggerFactory.getLogger(UserGraphService.class);
    private final Neo4jUserRepository neo4jUserRepository;

    @Autowired
    public UserGraphService(Neo4jUserRepository neo4jUserRepository) {
        this.neo4jUserRepository = neo4jUserRepository;
    }

    public void createUserInGraph(User user) {
        UserNode userNode = new UserNode(user.getId(), user.getFirstName(), user.getEmail());
        neo4jUserRepository.save(userNode);
    }

    public void deleteUserInGraph(String userId) {
        neo4jUserRepository.deleteById(userId);
    }

    public void subscribe(String subscriberId, String subscribedUserId) {
        UserNode subscriber = neo4jUserRepository.findByUserId(subscriberId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + subscriberId));
        UserNode subscribedUser = neo4jUserRepository.findByUserId(subscribedUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + subscribedUserId));

        if (!subscriber.getSubscriptions().contains(subscribedUser)) {
            subscriber.getSubscriptions().add(subscribedUser);
            neo4jUserRepository.save(subscriber);
        }
    }

    public void unsubscribe(String subscriberId, String subscribedUserId) {
        UserNode subscriber = neo4jUserRepository.findByUserId(subscriberId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + subscriberId));
        UserNode subscribedUser = neo4jUserRepository.findByUserId(subscribedUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + subscribedUserId));

        subscriber.getSubscriptions().remove(subscribedUser);
        neo4jUserRepository.save(subscriber);
    }

    public void createFriendshipInGraph(String userId1, String userId2) {
        UserNode user1 = neo4jUserRepository.findByUserId(userId1)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId1));
        UserNode user2 = neo4jUserRepository.findByUserId(userId2)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId2));
        user1.getFriends().add(user2);
        logger.info("Creating user in graph: {}", user1);
        neo4jUserRepository.save(user1);
    }

    public boolean isConnected(String userId1, String userId2) {
        return neo4jUserRepository.existsById(userId1) && neo4jUserRepository.existsById(userId2);
    }

    public int getDistance(String userId1, String userId2) {
        String query = "MATCH (u1:User {userId: $userId1}), (u2:User {userId: $userId2}), " +
                "p = shortestPath((u1)-[:FRIEND*]-(u2)) " +
                "RETURN length(p) as distance";

        Map<String, Object> parameters = Map.of("userId1", userId1, "userId2", userId2);

        return neo4jUserRepository.runCustomQueryForDistance(query, parameters.toString());
    }

    public void removeFriendshipInGraph(String userId1, String userId2) {
        UserNode user1 = neo4jUserRepository.findByUserId(userId1).orElseThrow();
        UserNode user2 = neo4jUserRepository.findByUserId(userId2).orElseThrow();
        user1.getFriends().remove(user2);
        neo4jUserRepository.save(user1);
    }
}

