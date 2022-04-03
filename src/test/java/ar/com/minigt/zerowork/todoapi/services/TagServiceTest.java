package ar.com.minigt.zerowork.todoapi.services;

import static ar.com.minigt.zerowork.todoapi.builders.TagBuilder.*;
import static ar.com.minigt.zerowork.todoapi.builders.TagDocumentBuilder.*;
import static ar.com.minigt.zerowork.todoapi.exceptions.TodoErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ar.com.minigt.zerowork.todoapi.TodoApplicationTest;
import ar.com.minigt.zerowork.todoapi.entities.TagDocument;
import ar.com.minigt.zerowork.todoapi.exceptions.TodoException;
import ar.com.minigt.zerowork.todoapi.models.Tag;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.test.context.support.WithMockUser;

public class TagServiceTest extends TodoApplicationTest {

    @Autowired
    TagService tagService;
    @Autowired
    MongoTemplate template;

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void createTag() {
        Tag tag = standardTag()
            .withId(null)
            .withUsername(null)
            .build();
        Tag result = tagService.save(tag);
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(tag.getName());
        assertThat(result.getUsername()).isNotNull();
        assertThat(result.getId()).isNotNull();
    }

    @Test
    public void createTagWithNullName() {
        Tag tag = standardTag()
            .withName(null)
            .build();
        assertThatThrownBy(() -> tagService.save(tag))
            .isInstanceOf(TodoException.class)
            .hasMessageContaining(TAG_NAME_NOT_NULL.getReasonPhrase());
    }

    @Test
    public void createTagWithoutName() {
        Tag tag = standardTag()
            .withName("")
            .build();
        assertThatThrownBy(() -> tagService.save(tag))
            .isInstanceOf(TodoException.class)
            .hasMessageContaining(TAG_NAME_NOT_EMPTY.getReasonPhrase());
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void updateTagName() {
        Tag tag = standardTag().build();
        tag = tagService.save(tag);
        Tag update = new Tag();
        update.setId(tag.getId());
        update.setName(tag.getName() + " some append");
        Tag result = tagService.update(update);
        assertThat(result).usingRecursiveComparison().ignoringFields("name");
        assertThat(result.getName()).isEqualTo(update.getName());
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void updateTagInvalidId() {
        Tag tag = standardTag().build();
        tag = tagService.save(tag);
        Tag update = new Tag();
        assertThatThrownBy(() -> tagService.update(update))
            .isInstanceOf(TodoException.class)
            .hasMessageContaining(TAG_ID_CANT_BE_NULL.getReasonPhrase());
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void updateTagNotFound() {
        Tag tag = standardTag().build();
        tag = tagService.save(tag);
        Tag update = new Tag();
        update.setName(tag.getName() + " append some change");
        update.setId("some invalid id");
        assertThatThrownBy(() -> tagService.update(update))
            .isInstanceOf(TodoException.class)
            .hasMessageContaining(TAG_NOT_FOUND.getReasonPhrase());
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void updateTagInvalidUser() {
        TagDocument tag = standardTagDocument()
            .withUsername("some_other_username")
            .persist(template);

        Tag update = new Tag();
        update.setId(tag.getId());
        update.setName(tag.getName() + " append some change");
        assertThatThrownBy(() -> tagService.update(update))
            .isInstanceOf(TodoException.class)
            .hasMessageContaining(TAG_NOT_FOUND.getReasonPhrase());
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void getTagsByUser() {
        standardTagDocument()
            .persist(template);
        standardTagDocument()
            .persist(template);
        standardTagDocument()
            .withUsername("some_other_username")
            .persist(template);
        List<Tag> tags = tagService.find();
        assertThat(tags.size()).isEqualTo(2);
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void getTodosByUserAndId() {
        TagDocument tag = standardTagDocument()
            .persist(template);
        Tag result = tagService.findById(tag.getId());
        assertThat(result.getId()).isEqualTo(tag.getId());
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void getTodosByUserAndIdInvalidUser() {
        TagDocument tag = standardTagDocument()
            .withUsername("some other username")
            .persist(template);
        assertThatThrownBy(() -> tagService.findById(tag.getId()))
            .isInstanceOf(TodoException.class)
            .hasMessageContaining(INVALID_TAG_ID.getReasonPhrase());
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void testDeleteTag() {
        TagDocument tag = standardTagDocument()
            .persist(template);
        Tag deletedTag = tagService.delete(tag.getId());
        assertThat(deletedTag.getId()).isEqualTo(tag.getId());
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void testDeleteTagInvalidUser() {
        TagDocument tag = standardTagDocument()
            .withUsername("some other user")
            .persist(template);
        assertThatThrownBy(() -> tagService.delete(tag.getId()))
            .isInstanceOf(TodoException.class)
            .hasMessageContaining(INVALID_TAG_ID.getReasonPhrase());
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void testDeleteTodoInvalidId() {
        TagDocument tag = standardTagDocument()
            .persist(template);
        assertThatThrownBy(() -> tagService.delete("some invalid id"))
            .isInstanceOf(TodoException.class)
            .hasMessageContaining(INVALID_TAG_ID.getReasonPhrase());
    }
}
