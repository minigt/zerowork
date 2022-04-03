package ar.com.minigt.zerowork.todoapi.services;

import static ar.com.minigt.zerowork.todoapi.builders.TagDocumentBuilder.*;
import static ar.com.minigt.zerowork.todoapi.builders.TodoBuilder.standardTodo;
import static ar.com.minigt.zerowork.todoapi.builders.TodoDocumentBuilder.standardTodoDocument;
import static ar.com.minigt.zerowork.todoapi.builders.TodoTagDocumentBuilder.*;
import static ar.com.minigt.zerowork.todoapi.enums.State.POSTPONED;
import static ar.com.minigt.zerowork.todoapi.exceptions.TodoErrorCode.DESCRIPTION_NOT_EMPTY;
import static ar.com.minigt.zerowork.todoapi.exceptions.TodoErrorCode.DESCRIPTION_NOT_NULL;
import static ar.com.minigt.zerowork.todoapi.exceptions.TodoErrorCode.INVALID_STATE;
import static ar.com.minigt.zerowork.todoapi.exceptions.TodoErrorCode.INVALID_TODO_ID;
import static ar.com.minigt.zerowork.todoapi.exceptions.TodoErrorCode.INVALID_TODO_TAG_ID;
import static ar.com.minigt.zerowork.todoapi.exceptions.TodoErrorCode.STATE_NOT_NULL;
import static ar.com.minigt.zerowork.todoapi.exceptions.TodoErrorCode.TAG_NOT_FOUND;
import static ar.com.minigt.zerowork.todoapi.exceptions.TodoErrorCode.TITTLE_NOT_EMPTY;
import static ar.com.minigt.zerowork.todoapi.exceptions.TodoErrorCode.TITTLE_NOT_NULL;
import static ar.com.minigt.zerowork.todoapi.exceptions.TodoErrorCode.TODO_ID_CANT_BE_NULL;
import static ar.com.minigt.zerowork.todoapi.exceptions.TodoErrorCode.TODO_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ar.com.minigt.zerowork.todoapi.TodoApplicationTest;
import ar.com.minigt.zerowork.todoapi.builders.TodoTagDocumentBuilder;
import ar.com.minigt.zerowork.todoapi.entities.TagDocument;
import ar.com.minigt.zerowork.todoapi.entities.TodoDocument;
import ar.com.minigt.zerowork.todoapi.entities.TodoTagDocument;
import ar.com.minigt.zerowork.todoapi.exceptions.TodoException;
import ar.com.minigt.zerowork.todoapi.models.Todo;
import ar.com.minigt.zerowork.todoapi.models.TodoTag;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.test.context.support.WithMockUser;

public class TodoTagServiceTest extends TodoApplicationTest {

    @Autowired
    TodoTagService todoTagService;
    @Autowired
    MongoTemplate template;

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void createTodoTagRelationship() {
        TodoDocument todo = standardTodoDocument()
            .persist(template);
        TagDocument tag = standardTagDocument()
            .persist(template);
        TodoTag result = todoTagService.link(todo.getId(), tag.getId());
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getUsername()).isNotNull();
        assertThat(result.getTodo()).usingRecursiveComparison().isEqualTo(todo);
        assertThat(result.getTag()).usingRecursiveComparison().isEqualTo(tag);
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void createTodoTagWithInvalidTodoId() {
        TodoDocument todo = standardTodoDocument()
            .persist(template);
        TagDocument tag = standardTagDocument()
            .persist(template);
        assertThatThrownBy(() -> todoTagService.link("notvalid", tag.getId()))
            .isInstanceOf(TodoException.class)
            .hasMessageContaining(TODO_NOT_FOUND.getReasonPhrase());
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void createTodoTagWithInvalidTagId() {
        TodoDocument todo = standardTodoDocument()
            .persist(template);
        TagDocument tag = standardTagDocument()
            .persist(template);
        assertThatThrownBy(() -> todoTagService.link(todo.getId(), "notvalid"))
            .isInstanceOf(TodoException.class)
            .hasMessageContaining(TAG_NOT_FOUND.getReasonPhrase());
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void linkTodoInvalidTodoUser() {
        TodoDocument todo = standardTodoDocument()
            .withUsername("some_other_username")
            .persist(template);
        TagDocument tag = standardTagDocument()
            .persist(template);
        assertThatThrownBy(() -> todoTagService.link(todo.getId(), tag.getId()))
            .isInstanceOf(TodoException.class)
            .hasMessageContaining(TODO_NOT_FOUND.getReasonPhrase());
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void linkTodoInvalidTagUser() {
        TodoDocument todo = standardTodoDocument()
            .persist(template);
        TagDocument tag = standardTagDocument()
            .withUsername("some_other_username")
            .persist(template);
        assertThatThrownBy(() -> todoTagService.link(todo.getId(), tag.getId()))
            .isInstanceOf(TodoException.class)
            .hasMessageContaining(TAG_NOT_FOUND.getReasonPhrase());
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void getTodoTagByUser() {
        standardTodoTagDocument(template)
            .persist(template);
        standardTodoTagDocument(template)
            .persist(template);
        standardTodoTagDocument(template)
            .withUsername("some_other_username")
            .persist(template);

        List<TodoTag> todoTags = todoTagService.find();
        assertThat(todoTags.size()).isEqualTo(2);
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void getTodosByUserAndId() {
        TodoTagDocument todoTag = standardTodoTagDocument(template)
            .persist(template);

        TodoTag result = todoTagService.findById(todoTag.getId());
        assertThat(result.getId()).isEqualTo(todoTag.getId());
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void getTodosByUserAndIdInvalidUser() {
        TodoTagDocument todoTag = standardTodoTagDocument(template)
            .withUsername("someInvalidUser")
            .persist(template);

        assertThatThrownBy(() -> todoTagService.findById(todoTag.getId()))
            .isInstanceOf(TodoException.class)
            .hasMessageContaining(INVALID_TODO_TAG_ID.getReasonPhrase());
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void testDeleteTodo() {
        TodoTagDocument todoTag = standardTodoTagDocument(template)
            .persist(template);

        TodoTag deletedTodoTag = todoTagService.delete(todoTag.getId());
        assertThat(deletedTodoTag.getId()).isEqualTo(todoTag.getId());
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void testDeleteTodoInvalidUser() {
        TodoTagDocument todoTag = standardTodoTagDocument(template)
            .withUsername("some_invalid_username")
            .persist(template);

        assertThatThrownBy(() -> todoTagService.delete(todoTag.getId()))
            .isInstanceOf(TodoException.class)
            .hasMessageContaining(INVALID_TODO_TAG_ID.getReasonPhrase());
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void testDeleteTodoTagInvalidId() {
        TodoTagDocument todoTag = standardTodoTagDocument(template)
            .persist(template);
        assertThatThrownBy(() -> todoTagService.delete("some invalid id"))
            .isInstanceOf(TodoException.class)
            .hasMessageContaining(INVALID_TODO_TAG_ID.getReasonPhrase());
    }

}
