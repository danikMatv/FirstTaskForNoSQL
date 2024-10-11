package org.example.taskonefornosql.Repository;

import org.example.taskonefornosql.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
}

