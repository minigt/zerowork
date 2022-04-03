package ar.com.minigt.zerowork.todoapi.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Todo {

    private String id;

    private String tittle;

    private String description;

    private String state;

    private String username;

}
