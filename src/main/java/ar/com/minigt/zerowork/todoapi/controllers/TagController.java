package ar.com.minigt.zerowork.todoapi.controllers;

import ar.com.minigt.zerowork.todoapi.models.Tag;
import ar.com.minigt.zerowork.todoapi.services.TagService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping("")
    private Tag save(@RequestBody Tag tag) {
        return tagService.save(tag);
    }

    @PutMapping("")
    private Tag update(@RequestBody Tag tag) {
        return tagService.update(tag);
    }

    @GetMapping("/{id}")
    private Tag findById(@PathVariable String id) {
        return tagService.findById(id);
    }

    @GetMapping("")
    private List<Tag> find() {
        return tagService.find();
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable String id) {
        tagService.delete(id);
    }
}