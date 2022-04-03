package ar.com.minigt.zerowork.todoapi.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(value = "users")
@Getter
@Setter
public class UserDocument {

    @Id
    private String id;

    private String username;

    @Field(name = "password_hash")
    private String passwordHash;

}