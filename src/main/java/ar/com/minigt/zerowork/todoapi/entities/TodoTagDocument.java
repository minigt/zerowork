package ar.com.minigt.zerowork.todoapi.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("todo_tag_links")
@Getter
@Setter
public class TodoTagDocument {

    private String id;

    @Field("todo_id")
    private String todoId;

    @Field("tag_id")
    private String tagId;

    private String username;

}
