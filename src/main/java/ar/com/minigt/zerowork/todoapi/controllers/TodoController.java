package ar.com.minigt.zerowork.todoapi.controllers;

import ar.com.minigt.zerowork.todoapi.models.Todo;
import ar.com.minigt.zerowork.todoapi.services.TodoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PutMapping("")
    private Todo update(@RequestBody Todo todo) {
        return todoService.update(todo);
    }

    @GetMapping("/{id}")
    private Todo findById(@PathVariable String id) {
        return todoService.findById(id);
    }

    @GetMapping("")
    private List<Todo> find() {
        return todoService.find();
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable String id) {
        todoService.delete(id);
    }
}