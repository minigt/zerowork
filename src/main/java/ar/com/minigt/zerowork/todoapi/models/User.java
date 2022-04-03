package ar.com.minigt.zerowork.todoapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private String id;

    private String username;

    private String password;

    @JsonProperty("password_repeated")
    private String passwordRepeated;

    @JsonProperty("password_hash")
    private String passwordHash;

}