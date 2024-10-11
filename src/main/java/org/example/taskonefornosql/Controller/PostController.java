package org.example.taskonefornosql.Controller;

import org.example.taskonefornosql.Entity.Post;
import org.example.taskonefornosql.Service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        return ResponseEntity.ok(postService.createPost(post));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Post>> getPostsByUser(@PathVariable String userId) {
        return ResponseEntity.ok(postService.getPostsByUserId(userId));
    }

    @PostMapping("/{postId}")
    public ResponseEntity<Post> addReaction(@PathVariable String postId, @RequestBody List<String> reaction) {
        postService.addReactionToPost(postId, reaction);
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Post> removeReaction(@PathVariable String postId, @PathVariable String reaction) {
        postService.removeReactionFromPost(postId, reaction);
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllPostsSorted() {
        return ResponseEntity.ok(postService.getAllPostsSortedByDate());
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @PostMapping("/{postId}/comment")
    public ResponseEntity<Post> addComment(@PathVariable String postId, @RequestBody String comment) {
        return ResponseEntity.ok(postService.addCommentToPost(postId, comment));
    }
}
