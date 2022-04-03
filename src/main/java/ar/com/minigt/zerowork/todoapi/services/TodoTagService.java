package ar.com.minigt.zerowork.todoapi.services;

import static ar.com.minigt.zerowork.todoapi.exceptions.TodoErrorCode.*;

import ar.com.minigt.zerowork.todoapi.entities.TagDocument;
import ar.com.minigt.zerowork.todoapi.entities.TodoDocument;
import ar.com.minigt.zerowork.todoapi.entities.TodoTagDocument;
import ar.com.minigt.zerowork.todoapi.exceptions.TodoException;
import ar.com.minigt.zerowork.todoapi.models.TodoTag;
import ar.com.minigt.zerowork.todoapi.repositories.TagRepository;
import ar.com.minigt.zerowork.todoapi.repositories.TodoRepository;
import ar.com.minigt.zerowork.todoapi.repositories.TodoTagRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class TodoTagService {

    private final TodoTagRepository todoTagRepository;
    private final TodoRepository todoRepository;
    private final TagRepository tagRepository;
    private final ConversionService conversionService;
    private final UserService userService;

    public TodoTagService(TodoTagRepository todoTagRepository,
                          ConversionService conversionService,
                          UserService userService,
                          TodoRepository todoRepository,
                          TagRepository tagRepository) {
        this.todoTagRepository = todoTagRepository;
        this.conversionService = conversionService;
        this.userService = userService;
        this.todoRepository = todoRepository;
        this.tagRepository = tagRepository;
    }

    public TodoTag link(String todoId, String tagId) {
        validateTodoTagLink(todoId, tagId);
        TodoTagDocument entity = new TodoTagDocument();
        entity.setTagId(tagId);
        entity.setTodoId(todoId);
        entity.setUsername(userService.getCurrentUsername());
        entity = todoTagRepository.save(entity);
        return conversionService.convert(entity, TodoTag.class);
    }

    private void validateTodoTagLink(String todoId, String tagId) {
        String username = userService.getCurrentUsername();
        Optional<TodoDocument> todoDocument = todoRepository.findByIdAndUsername(todoId, username);
        if (!todoDocument.isPresent()) {
            throw new TodoException(TODO_NOT_FOUND);
        }
        Optional<TagDocument> tagDocument = tagRepository.findByIdAndUsername(tagId, username);
        if (!tagDocument.isPresent()) {
            throw new TodoException(TAG_NOT_FOUND);
        }
        if (todoTagRepository.findByTodoIdAndTagId(todoId, tagId).isPresent()) {
            throw new TodoException(TODO_TAG_RELATIONSHIP_ALREADY_EXISTS);
        }
    }

    public List<TodoTag> find() {
        List<TodoTagDocument> documents = todoTagRepository.findByUsername(userService.getCurrentUsername());
        List<TodoTag> todosTags = new ArrayList();
        documents.forEach(document -> todosTags.add(conversionService.convert(document, TodoTag.class)));
        return todosTags;
    }

    public TodoTag findById(String id) {
        Optional<TodoTagDocument> todoTag = todoTagRepository.findByIdAndUsername(id, userService.getCurrentUsername());
        if (!todoTag.isPresent()) {
            throw new TodoException(INVALID_TODO_TAG_ID);
        }
        return conversionService.convert(todoTag.get(), TodoTag.class);
    }

    public TodoTag delete(String id) {
        Optional<TodoTagDocument> deleteTodoTag = todoTagRepository.deleteByIdAndUsername(id, userService.getCurrentUsername());
        if (!deleteTodoTag.isPresent()) {
            throw new TodoException(INVALID_TODO_TAG_ID);
        }
        return conversionService.convert(deleteTodoTag.get(), TodoTag.class);
    }

}
