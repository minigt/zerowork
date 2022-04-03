package ar.com.minigt.zerowork.todoapi.builders;

import ar.com.minigt.zerowork.todoapi.entities.TodoDocument;
import ar.com.minigt.zerowork.todoapi.enums.State;
import ar.com.minigt.zerowork.todoapi.models.Todo;
import java.util.UUID;

public class TodoBuilder {

    private Todo instance;

    public static TodoBuilder standardTodo() {
        TodoBuilder builder = new TodoBuilder();
        builder.instance = new Todo();
        builder.instance.setTittle("a tittle");
        builder.instance.setDescription("a description");
        builder.instance.setState(State.PENDING.name());
        builder.instance.setId(UUID.randomUUID().toString());
        builder.instance.setUsername("zerowork");
        return builder;
    }

    public Todo build() {
        return instance;
    }

    public TodoBuilder withId(String id) {
        instance.setId(id);
        return this;
    }

    public TodoBuilder withUsername(String username) {
        instance.setUsername(username);
        return this;
    }

    public TodoBuilder withTittle(String tittle) {
        instance.setTittle(tittle);
        return this;
    }

    public TodoBuilder withDescription(String description) {
        instance.setDescription(description);
        return this;
    }

    public TodoBuilder withState(String state) {
        instance.setState(state);
        return this;
    }

}
