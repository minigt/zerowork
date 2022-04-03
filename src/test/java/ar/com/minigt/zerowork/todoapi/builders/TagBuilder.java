package ar.com.minigt.zerowork.todoapi.builders;

import ar.com.minigt.zerowork.todoapi.models.Tag;
import java.util.UUID;

public class TagBuilder {

    private Tag instance;

    public static TagBuilder standardTag() {
        TagBuilder builder = new TagBuilder();
        builder.instance = new Tag();
        builder.instance.setId(UUID.randomUUID().toString());
        builder.instance.setUsername("zerowork");
        builder.instance.setName("aTagName");
        return builder;
    }

    public Tag build() {
        return instance;
    }

    public TagBuilder withId(String id) {
        instance.setId(id);
        return this;
    }

    public TagBuilder withUsername(String username) {
        instance.setUsername(username);
        return this;
    }

    public TagBuilder withName(String name) {
        instance.setName(name);
        return this;
    }

}
