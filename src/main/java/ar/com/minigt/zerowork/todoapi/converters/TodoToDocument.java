package ar.com.minigt.zerowork.todoapi.converters;

import ar.com.minigt.zerowork.todoapi.entities.TodoDocument;
import ar.com.minigt.zerowork.todoapi.models.Todo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TodoToDocument implements Converter<Todo, TodoDocument> {

    @Override
    public TodoDocument convert(Todo source) {
        TodoDocument document = new TodoDocument();
        document.setTittle(source.getTittle());
        document.setDescription(source.getDescription());
        document.setState(source.getState());
        return document;
    }
}
