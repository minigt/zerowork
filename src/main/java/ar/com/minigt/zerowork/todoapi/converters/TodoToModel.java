package ar.com.minigt.zerowork.todoapi.converters;

import ar.com.minigt.zerowork.todoapi.entities.TodoDocument;
import ar.com.minigt.zerowork.todoapi.models.Todo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TodoToModel implements Converter<TodoDocument, Todo> {

    @Override
    public Todo convert(TodoDocument source) {
        Todo model = new Todo();
        model.setId(source.getId());
        model.setTittle(source.getTittle());
        model.setDescription(source.getDescription());
        model.setState(source.getState());
        model.setUserId(source.getUserId());
        return model;
    }
}
