package org.example.taskonefornosql.Repository.Neo4jRepository;

import org.example.taskonefornosql.Node.UserNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Neo4jUserRepository extends Neo4jRepository<UserNode, String> {
    @Query("MATCH (u:User {userId: $userId}) RETURN u")
    Optional<UserNode> findByUserId(@Param("userId") String userId);


    @Query("MATCH (u1:User {userId: $userId1}), (u2:User {userId: $userId2}), " +
            "p = shortestPath((u1)-[:FRIEND*]-(u2)) " +
            "RETURN length(p)")
    int runCustomQueryForDistance(@Param("userId1") String userId1, @Param("userId2") String userId2);

}

