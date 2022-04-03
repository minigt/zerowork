package ar.com.minigt.zerowork.todoapi.builders;

import static ar.com.minigt.zerowork.todoapi.builders.TagDocumentBuilder.*;
import static ar.com.minigt.zerowork.todoapi.builders.TodoDocumentBuilder.*;

import ar.com.minigt.zerowork.todoapi.entities.TagDocument;
import ar.com.minigt.zerowork.todoapi.entities.TodoTagDocument;
import java.util.UUID;
import org.springframework.data.mongodb.core.MongoTemplate;

public class TodoTagDocumentBuilder {

    private TodoTagDocument instance;

    public static TodoTagDocumentBuilder standardTodoTagDocument(MongoTemplate template) {
        TodoTagDocumentBuilder builder = new TodoTagDocumentBuilder();
        builder.instance = new TodoTagDocument();
        builder.instance.setId(UUID.randomUUID().toString());
        builder.instance.setUsername("zerowork");
        builder.instance.setTodoId(standardTodoDocument().persist(template).getId());
        builder.instance.setTagId(standardTagDocument().persist(template).getId());
        return builder;
    }

    public TodoTagDocument build() {
        return instance;
    }

    public TodoTagDocumentBuilder withId(String id) {
        instance.setId(id);
        return this;
    }

    public TodoTagDocumentBuilder withUsername(String username) {
        instance.setUsername(username);
        return this;
    }

    public TodoTagDocumentBuilder withTodoId(String todoId) {
        instance.setTodoId(todoId);
        return this;
    }

    public TodoTagDocumentBuilder withTagId(String tagId) {
        instance.setTagId(tagId);
        return this;
    }

    public TodoTagDocument persist(MongoTemplate template) {
        return template.save(instance);
    }

}
