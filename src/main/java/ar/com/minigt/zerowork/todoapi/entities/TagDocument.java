package ar.com.minigt.zerowork.todoapi.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tags")
@Getter
@Setter
public class TagDocument {

    private String id;

    private String username;

    private String name;

}
