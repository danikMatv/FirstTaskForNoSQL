package org.example.taskonefornosql.Service;

import org.example.taskonefornosql.Entity.Post;
import org.example.taskonefornosql.Repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public List<Post> getPostsByUserId(String userId) {
        return postRepository.findByAuthorId(userId);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(String postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public List<Post> getAllPostsSortedByDate() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    public void addReactionToPost(String postId, List<String> reaction) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        for (String s : reaction) {
            post.setReactions(reaction);
        }
        postRepository.save(post);
    }

    public void removeReactionFromPost(String postId, String reaction) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        post.getReactions().remove(reaction);
        postRepository.save(post);
    }

    public Post addCommentToPost(String postId, String comment) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        post.getComments().add(comment);  // Додаємо коментар до посту
        return postRepository.save(post);
    }
}

