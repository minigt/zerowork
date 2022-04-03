package ar.com.minigt.zerowork.todoapi.converters;

import ar.com.minigt.zerowork.todoapi.entities.TagDocument;
import ar.com.minigt.zerowork.todoapi.entities.TodoTagDocument;
import ar.com.minigt.zerowork.todoapi.models.Tag;
import ar.com.minigt.zerowork.todoapi.models.TodoTag;
import ar.com.minigt.zerowork.todoapi.repositories.TagRepository;
import ar.com.minigt.zerowork.todoapi.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TodoTagToModel implements Converter<TodoTagDocument, TodoTag> {

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private TodoToModel todoToModel;
    @Autowired
    private TagToModel tagToModel;

    @Override
    public TodoTag convert(TodoTagDocument source) {
        TodoTag model = new TodoTag();
        model.setId(source.getId());
        model.setUsername(source.getUsername());
        model.setTag(tagToModel.convert(tagRepository.findById(source.getTagId()).get()));
        model.setTodo(todoToModel.convert(todoRepository.findById(source.getTodoId()).get()));
        return model;
    }
}
