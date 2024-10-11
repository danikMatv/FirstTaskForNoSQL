package org.example.taskonefornosql.Repository;

import org.example.taskonefornosql.Entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findAllByOrderByCreatedAtDesc();
    List<Post> findByAuthorId(String authorId);  // Знайти пости за ідентифікатором користувача
}

