package ar.com.minigt.zerowork.todoapi.converters;

import ar.com.minigt.zerowork.todoapi.entities.TagDocument;
import ar.com.minigt.zerowork.todoapi.models.Tag;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TagToModel implements Converter<TagDocument, Tag> {

    @Override
    public Tag convert(TagDocument source) {
        Tag model = new Tag();
        model.setId(source.getId());
        model.setUsername(source.getUsername());
        model.setName(source.getName());
        return model;
    }
}
