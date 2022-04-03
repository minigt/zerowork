package ar.com.minigt.zerowork.todoapi.controllers;

import ar.com.minigt.zerowork.todoapi.models.Todo;
import ar.com.minigt.zerowork.todoapi.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping("")
    private Todo save(@RequestBody Todo todo) {
        return todoService.save(todo);
    }

}