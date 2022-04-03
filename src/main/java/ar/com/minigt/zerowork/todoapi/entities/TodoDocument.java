package ar.com.minigt.zerowork.todoapi.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("todos")
@Getter
@Setter
public class TodoDocument {

    private String id;

    private String tittle;

    private String description;

    private String state;

    private String username;

}
