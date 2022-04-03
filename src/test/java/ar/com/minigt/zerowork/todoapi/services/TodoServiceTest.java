package ar.com.minigt.zerowork.todoapi.services;

import static ar.com.minigt.zerowork.todoapi.builders.TodoBuilder.*;
import static ar.com.minigt.zerowork.todoapi.exceptions.TodoErrorCode.*;
import static org.assertj.core.api.Assertions.*;

import ar.com.minigt.zerowork.todoapi.TodoApplicationTest;
import ar.com.minigt.zerowork.todoapi.exceptions.TodoException;
import ar.com.minigt.zerowork.todoapi.models.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TodoServiceTest extends TodoApplicationTest {

    @Autowired
    TodoService todoService;

    @Test
    public void createTodo() {
        Todo todo = standardTodo()
            .withId(null)
            .withUserId(null)
            .build();
        Todo result = todoService.save(todo);
        assertThat(result).isNotNull();
        assertThat(result.getTittle()).isEqualTo(todo.getTittle());
        assertThat(result.getDescription()).isEqualTo(todo.getDescription());
        assertThat(result.getState()).isEqualTo(todo.getState());
        assertThat(result.getUserId()).isNotNull();
        assertThat(result.getId()).isNotNull();
    }

    @Test
    public void createTodoWithNullTittle() {
        Todo todo = standardTodo()
            .withTittle(null)
            .build();
        assertThatThrownBy(() -> todoService.save(todo))
            .isInstanceOf(TodoException.class)
            .hasMessageContaining(TITTLE_NOT_NULL.getReasonPhrase());
    }

    @Test
    public void createTodoWithoutTittle() {
        Todo todo = standardTodo()
            .withTittle("")
            .build();
        assertThatThrownBy(() -> todoService.save(todo))
            .isInstanceOf(TodoException.class)
            .hasMessageContaining(TITTLE_NOT_EMPTY.getReasonPhrase());
    }

    @Test
    public void createTodoWithNullDescription() {
        Todo todo = standardTodo()
            .withDescription(null)
            .build();
        assertThatThrownBy(() -> todoService.save(todo))
            .isInstanceOf(TodoException.class)
            .hasMessageContaining(DESCRIPTION_NOT_NULL.getReasonPhrase());
    }

    @Test
    public void createTodoWithoutDescription() {
        Todo todo = standardTodo()
            .withDescription("")
            .build();
        assertThatThrownBy(() -> todoService.save(todo))
            .isInstanceOf(TodoException.class)
            .hasMessageContaining(DESCRIPTION_NOT_EMPTY.getReasonPhrase());
    }

    @Test
    public void createTodoWithNullState() {
        Todo todo = standardTodo()
            .withState(null)
            .build();
        assertThatThrownBy(() -> todoService.save(todo))
            .isInstanceOf(TodoException.class)
            .hasMessageContaining(STATE_NOT_NULL.getReasonPhrase());
    }

    @Test
    public void createTodoWithInvalidState() {
        Todo todo = standardTodo()
            .withState("some invalid state")
            .build();
        assertThatThrownBy(() -> todoService.save(todo))
            .isInstanceOf(TodoException.class)
            .hasMessageContaining(INVALID_STATE.getReasonPhrase());
    }

}
