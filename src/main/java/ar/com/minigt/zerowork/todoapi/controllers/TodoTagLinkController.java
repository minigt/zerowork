package ar.com.minigt.zerowork.todoapi.controllers;

import ar.com.minigt.zerowork.todoapi.models.TodoTag;
import ar.com.minigt.zerowork.todoapi.services.TodoTagService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todo-tag")
public class TodoTagLinkController {

    @Autowired
    private TodoTagService todoTagService;

    @PostMapping("/{todo_id}/{tag_id}")
    private TodoTag link(@PathVariable(name = "todo_id") String todoId, @PathVariable(name = "tag_id") String tagId) {
        return todoTagService.link(todoId, tagId);
    }

    @GetMapping("/{id}")
    private TodoTag findById(@PathVariable String id) {
        return todoTagService.findById(id);
    }

    @GetMapping("")
    private List<TodoTag> find() {
        return todoTagService.find();
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable String id) {
        todoTagService.delete(id);
    }

}