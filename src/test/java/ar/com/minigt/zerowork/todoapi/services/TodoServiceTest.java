package ar.com.minigt.zerowork.todoapi.services;

import static ar.com.minigt.zerowork.todoapi.builders.TodoBuilder.*;
import static ar.com.minigt.zerowork.todoapi.builders.TodoDocumentBuilder.*;
import static ar.com.minigt.zerowork.todoapi.enums.State.*;
import static ar.com.minigt.zerowork.todoapi.exceptions.TodoErrorCode.*;
import static org.assertj.core.api.Assertions.*;

import ar.com.minigt.zerowork.todoapi.TodoApplicationTest;
import ar.com.minigt.zerowork.todoapi.entities.TodoDocument;
import ar.com.minigt.zerowork.todoapi.exceptions.TodoException;
import ar.com.minigt.zerowork.todoapi.models.Todo;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.test.context.support.WithMockUser;

public class TodoServiceTest extends TodoApplicationTest {

    @Autowired
    TodoService todoService;
    @Autowired
    MongoTemplate template;

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void createTodo() {
        Todo todo = standardTodo()
            .withId(null)
            .withUsername(null)
            .build();
        Todo result = todoService.save(todo);
        assertThat(result).isNotNull();
        assertThat(result.getTittle()).isEqualTo(todo.getTittle());
        assertThat(result.getDescription()).isEqualTo(todo.getDescription());
        assertThat(result.getState()).isEqualTo(todo.getState());
        assertThat(result.getUsername()).isNotNull();
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

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void updateTodoTittle() {
        Todo todo = standardTodo().build();
        todo = todoService.save(todo);
        Todo update = new Todo();
        update.setId(todo.getId());
        update.setTittle(todo.getTittle() + " some append");
        Todo result = todoService.update(update);
        assertThat(result).usingRecursiveComparison().ignoringFields("tittle");
        assertThat(result.getTittle()).isEqualTo(update.getTittle());
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void updateTodoDescription() {
        Todo todo = standardTodo().build();
        todo = todoService.save(todo);
        Todo update = new Todo();
        update.setId(todo.getId());
        update.setDescription(todo.getDescription() + " some append");
        Todo result = todoService.update(update);
        assertThat(result).usingRecursiveComparison().ignoringFields("description");
        assertThat(result.getDescription()).isEqualTo(update.getDescription());
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void updateTodoState() {
        Todo todo = standardTodo().build();
        todo = todoService.save(todo);
        Todo update = new Todo();
        update.setId(todo.getId());
        update.setState(POSTPONED.name());
        Todo result = todoService.update(update);
        assertThat(result).usingRecursiveComparison().ignoringFields("state");
        assertThat(result.getState()).isEqualTo(update.getState());
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void updateTodoInvalidId() {
        Todo todo = standardTodo().build();
        todo = todoService.save(todo);
        Todo update = new Todo();
        assertThatThrownBy(() -> todoService.update(update))
            .isInstanceOf(TodoException.class)
            .hasMessageContaining(TODO_ID_CANT_BE_NULL.getReasonPhrase());
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void updateTodoNotFound() {
        Todo todo = standardTodo().build();
        todo = todoService.save(todo);
        Todo update = new Todo();
        update.setId("some invalid id");
        assertThatThrownBy(() -> todoService.update(update))
            .isInstanceOf(TodoException.class)
            .hasMessageContaining(TODO_NOT_FOUND.getReasonPhrase());
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void updateTodoInvalidUser() {
        TodoDocument todo = standardTodoDocument()
            .withUsername("some_other_username")
            .persist(template);

        Todo update = new Todo();
        update.setId(todo.getId());
        assertThatThrownBy(() -> todoService.update(update))
            .isInstanceOf(TodoException.class)
            .hasMessageContaining(TODO_NOT_FOUND.getReasonPhrase());
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void getTodosByUser() {
        standardTodoDocument()
            .persist(template);
        standardTodoDocument()
            .persist(template);
        standardTodoDocument()
            .withUsername("some_other_username")
            .persist(template);
        List<Todo> todos = todoService.find();
        assertThat(todos.size()).isEqualTo(2);
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void getTodosByUserAndId() {
        TodoDocument todo = standardTodoDocument()
            .persist(template);
        Todo result = todoService.findById(todo.getId());
        assertThat(result.getId()).isEqualTo(todo.getId());
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void getTodosByUserAndIdInvalidUser() {
        TodoDocument todo = standardTodoDocument()
            .withUsername("some other username")
            .persist(template);
        assertThatThrownBy(() -> todoService.findById(todo.getId()))
            .isInstanceOf(TodoException.class)
            .hasMessageContaining(INVALID_TODO_ID.getReasonPhrase());
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void testDeleteTodo() {
        TodoDocument todo = standardTodoDocument()
            .persist(template);
        Todo deletedTodo = todoService.delete(todo.getId());
        assertThat(deletedTodo.getId()).isEqualTo(todo.getId());
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void testDeleteTodoInvalidUser() {
        TodoDocument todo = standardTodoDocument()
            .withUsername("some other user")
            .persist(template);
        assertThatThrownBy(() -> todoService.delete(todo.getId()))
            .isInstanceOf(TodoException.class)
            .hasMessageContaining(INVALID_TODO_ID.getReasonPhrase());
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void testDeleteTodoInvalidId() {
        TodoDocument todo = standardTodoDocument()
            .persist(template);
        assertThatThrownBy(() -> todoService.delete("some invalid id"))
            .isInstanceOf(TodoException.class)
            .hasMessageContaining(INVALID_TODO_ID.getReasonPhrase());
    }

}
