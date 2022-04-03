package ar.com.minigt.zerowork.todoapi.builders;

import ar.com.minigt.zerowork.todoapi.entities.TagDocument;
import java.util.UUID;
import org.springframework.data.mongodb.core.MongoTemplate;

public class TagDocumentBuilder {

    private TagDocument instance;

    public static TagDocumentBuilder standardTagDocument() {
        TagDocumentBuilder builder = new TagDocumentBuilder();
        builder.instance = new TagDocument();
        builder.instance.setId(UUID.randomUUID().toString());
        builder.instance.setUsername("zerowork");
        builder.instance.setName("aTagName");
        return builder;
    }

    public TagDocument build() {
        return instance;
    }

    public TagDocumentBuilder withId(String id) {
        instance.setId(id);
        return this;
    }

    public TagDocumentBuilder withUsername(String username) {
        instance.setUsername(username);
        return this;
    }

    public TagDocumentBuilder withName(String name) {
        instance.setName(name);
        return this;
    }

    public TagDocument persist(MongoTemplate template) {
        return template.save(instance);
    }

}
