package ar.com.minigt.zerowork.todoapi.services;

import static ar.com.minigt.zerowork.todoapi.exceptions.TodoErrorCode.*;
import static org.apache.commons.lang3.StringUtils.*;

import ar.com.minigt.zerowork.todoapi.entities.TodoDocument;
import ar.com.minigt.zerowork.todoapi.enums.State;
import ar.com.minigt.zerowork.todoapi.exceptions.TodoErrorCode;
import ar.com.minigt.zerowork.todoapi.exceptions.TodoException;
import ar.com.minigt.zerowork.todoapi.models.Todo;
import ar.com.minigt.zerowork.todoapi.repositories.TodoRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final ConversionService conversionService;

    public TodoService(TodoRepository todoRepository, ConversionService conversionService) {
        this.todoRepository = todoRepository;
        this.conversionService = conversionService;
    }

    public Todo save(Todo todo) {
        validateTodoCreation(todo);
        TodoDocument entity = conversionService.convert(todo, TodoDocument.class);
        //TODO when security is implemented this has to be draw from context
        entity.setUserId("1");
        entity = todoRepository.save(entity);
        return conversionService.convert(entity, Todo.class);
    }

    private void validateTodoCreation(Todo todo) {
        if (todo.getTittle() == null) {
            throw new TodoException(TITTLE_NOT_NULL);
        }

        if (isBlank(todo.getTittle())) {
            throw new TodoException(TITTLE_NOT_EMPTY);
        }

        if (todo.getDescription() == null) {
            throw new TodoException(DESCRIPTION_NOT_NULL);
        }

        if (isBlank(todo.getDescription())) {
            throw new TodoException(DESCRIPTION_NOT_EMPTY);
        }

        if (todo.getState() == null) {
            throw new TodoException(STATE_NOT_NULL);
        }

        try {
            State.valueOf(todo.getState());
        } catch (Throwable t) {
            throw new TodoException(INVALID_STATE);
        }
    }
}
