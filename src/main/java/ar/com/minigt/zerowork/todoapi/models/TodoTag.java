package ar.com.minigt.zerowork.todoapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoTag {

    private String id;

    @JsonProperty("todo")
    private Todo todo;

    private Tag tag;

    private String username;

}
