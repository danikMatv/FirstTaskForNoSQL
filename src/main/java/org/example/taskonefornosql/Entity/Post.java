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
@Document(collection = "posts")
public class Post {
    @Id
    private String id;
    private String content;
    private String authorId;
    private Date createdAt;
    private List<String> comments;
    private List<String> reactions;

}
