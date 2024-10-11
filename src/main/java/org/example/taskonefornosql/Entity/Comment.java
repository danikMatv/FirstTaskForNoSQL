package org.example.taskonefornosql.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "comments")
public class Comment {
    @Id
    private String id;
    private String postId;
    private String authorId;
    private String content;
    private Date createdAt;
    private List<String> reactions;
}
