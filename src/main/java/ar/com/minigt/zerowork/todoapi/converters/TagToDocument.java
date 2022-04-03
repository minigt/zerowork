package ar.com.minigt.zerowork.todoapi.converters;

import ar.com.minigt.zerowork.todoapi.entities.TagDocument;
import ar.com.minigt.zerowork.todoapi.models.Tag;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TagToDocument implements Converter<Tag, TagDocument> {

    @Override
    public TagDocument convert(Tag source) {
        TagDocument document = new TagDocument();
        document.setName(source.getName());
        return document;
    }
}
