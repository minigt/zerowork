package ar.com.minigt.zerowork.todoapi.services;

import static ar.com.minigt.zerowork.todoapi.exceptions.TodoErrorCode.*;
import static org.apache.commons.lang3.StringUtils.*;

import ar.com.minigt.zerowork.todoapi.entities.TodoDocument;
import ar.com.minigt.zerowork.todoapi.enums.State;
import ar.com.minigt.zerowork.todoapi.exceptions.TodoException;
import ar.com.minigt.zerowork.todoapi.models.Todo;
import ar.com.minigt.zerowork.todoapi.repositories.TodoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final ConversionService conversionService;
    private final UserService userService;

    public TodoService(TodoRepository todoRepository,
                       ConversionService conversionService,
                       UserService userService) {
        this.todoRepository = todoRepository;
        this.conversionService = conversionService;
        this.userService = userService;
    }

    public Todo save(Todo todo) {
        validateTodoCreation(todo);
        TodoDocument entity = conversionService.convert(todo, TodoDocument.class);
        entity.setUsername(userService.getCurrentUsername());
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

    public Todo update(Todo update) {
        validateTodoUpdate(update);
        Optional<TodoDocument> document = todoRepository.findById(update.getId());
        if (!document.isPresent()) {
            throw new TodoException(TODO_NOT_FOUND);
        }
        TodoDocument todoDocumentUpdate = document.get();
        if (!userService.canAccessUser(todoDocumentUpdate.getUsername())) {
            throw new TodoException(TODO_NOT_FOUND);
        }
        if (update.getTittle() != null) {
            todoDocumentUpdate.setTittle(update.getTittle());
        }
        if (update.getDescription() != null) {
            todoDocumentUpdate.setDescription(update.getDescription());
        }
        if (update.getState() != null) {
            todoDocumentUpdate.setState(update.getState());
        }
        return conversionService.convert(todoRepository.save(todoDocumentUpdate), Todo.class);
    }

    private void validateTodoUpdate(Todo update) {
        if (update == null) {
            throw new TodoException(INVALID_REQUEST);
        }

        if (update.getId() == null) {
            throw new TodoException(TODO_ID_CANT_BE_NULL);
        }

    }

    public List<Todo> find() {
        List<TodoDocument> documents = todoRepository.findByUsername(userService.getCurrentUsername());
        List<Todo> todos = new ArrayList();
        documents.forEach(document -> todos.add(conversionService.convert(document, Todo.class)));
        return todos;
    }

    public Todo delete(String id) {
        Optional<TodoDocument> deleteTodo = todoRepository.deleteByIdAndUsername(id, userService.getCurrentUsername());
        if (!deleteTodo.isPresent()) {
            throw new TodoException(INVALID_TODO_ID);
        }
        return conversionService.convert(deleteTodo.get(), Todo.class);
    }

    public Todo findById(String id) {
        Optional<TodoDocument> todo = todoRepository.findByIdAndUsername(id, userService.getCurrentUsername());
        if (!todo.isPresent()) {
            throw new TodoException(INVALID_TODO_ID);
        }
        return conversionService.convert(todo.get(), Todo.class);
    }
}
