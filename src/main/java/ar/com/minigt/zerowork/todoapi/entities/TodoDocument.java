package ar.com.minigt.zerowork.todoapi.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("documents")
@Getter
@Setter
public class TodoDocument {

    private String id;

    private String tittle;

    private String description;

    private String state;

    @Field("user_id")
    private String userId;

}
