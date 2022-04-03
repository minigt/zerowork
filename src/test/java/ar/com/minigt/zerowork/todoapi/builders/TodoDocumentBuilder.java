package ar.com.minigt.zerowork.todoapi.builders;

import ar.com.minigt.zerowork.todoapi.entities.TodoDocument;
import ar.com.minigt.zerowork.todoapi.enums.State;
import ar.com.minigt.zerowork.todoapi.models.Todo;
import java.util.UUID;
import org.springframework.data.mongodb.core.MongoTemplate;

public class TodoDocumentBuilder {

    private TodoDocument instance;

    public static TodoDocumentBuilder standardTodoDocument() {
        TodoDocumentBuilder builder = new TodoDocumentBuilder();
        builder.instance = new TodoDocument();
        builder.instance.setTittle("a tittle");
        builder.instance.setDescription("a description");
        builder.instance.setState(State.PENDING.name());
        builder.instance.setId(UUID.randomUUID().toString());
        builder.instance.setUsername("zerowork");
        return builder;
    }

    public TodoDocument build() {
        return instance;
    }

    public TodoDocumentBuilder withId(String id) {
        instance.setId(id);
        return this;
    }

    public TodoDocumentBuilder withUsername(String username) {
        instance.setUsername(username);
        return this;
    }

    public TodoDocumentBuilder withTittle(String tittle) {
        instance.setTittle(tittle);
        return this;
    }

    public TodoDocumentBuilder withDescription(String description) {
        instance.setDescription(description);
        return this;
    }

    public TodoDocumentBuilder withState(String state) {
        instance.setState(state);
        return this;
    }

    public TodoDocument persist(MongoTemplate template) {
        return template.save(instance);
    }

}
