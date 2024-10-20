package org.example.taskonefornosql.Node;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Node("User")
public class UserNode {
    @Id
    private String userId;
    private String name;
    private String email;

    @Relationship(type = "Subscribe", direction = Relationship.Direction.OUTGOING)
    private List<UserNode> subscriptions = new ArrayList<>();
    @Relationship(type = "FRIEND", direction = Relationship.Direction.OUTGOING)
    private Set<UserNode> friends = new HashSet<>();

    public UserNode(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

}
