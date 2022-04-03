package ar.com.minigt.zerowork.todoapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Todo {

    private String id;

    private String tittle;

    private String description;

    private String state;

    @JsonProperty("user_id")
    private String userId;

}
