package ar.com.minigt.zerowork.todoapi.services;

import static ar.com.minigt.zerowork.todoapi.exceptions.TodoErrorCode.*;
import static ar.com.minigt.zerowork.todoapi.exceptions.TodoErrorCode.TAG_NAME_NOT_NULL;
import static org.apache.commons.lang3.StringUtils.isBlank;

import ar.com.minigt.zerowork.todoapi.entities.TagDocument;
import ar.com.minigt.zerowork.todoapi.exceptions.TodoException;
import ar.com.minigt.zerowork.todoapi.models.Tag;
import ar.com.minigt.zerowork.todoapi.repositories.TagRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final ConversionService conversionService;
    private final UserService userService;

    public TagService(TagRepository tagRepository,
                      ConversionService conversionService,
                      UserService userService) {
        this.tagRepository = tagRepository;
        this.conversionService = conversionService;
        this.userService = userService;
    }

    public Tag save(Tag tag) {
        validateTagCreation(tag);
        TagDocument entity = conversionService.convert(tag, TagDocument.class);
        entity.setUsername(userService.getCurrentUsername());
        entity = tagRepository.save(entity);
        return conversionService.convert(entity, Tag.class);
    }

    private void validateTagCreation(Tag tag) {
        if (tag.getName() == null) {
            throw new TodoException(TAG_NAME_NOT_NULL);
        }

        if (isBlank(tag.getName())) {
            throw new TodoException(TAG_NAME_NOT_EMPTY);
        }
    }

    public Tag update(Tag update) {
        validateTagUpdate(update);
        Optional<TagDocument> document = tagRepository.findById(update.getId());
        if (!document.isPresent()) {
            throw new TodoException(TAG_NOT_FOUND);
        }
        TagDocument tagDocumentUpdate = document.get();
        if (!userService.canAccessUser(tagDocumentUpdate.getUsername())) {
            throw new TodoException(TAG_NOT_FOUND);
        }
        tagDocumentUpdate.setName(update.getName());
        return conversionService.convert(tagRepository.save(tagDocumentUpdate), Tag.class);
    }

    private void validateTagUpdate(Tag update) {
        if (update == null) {
            throw new TodoException(INVALID_REQUEST);
        }

        if (update.getId() == null) {
            throw new TodoException(TAG_ID_CANT_BE_NULL);
        }

        if (update.getName() == null) {
            throw new TodoException(TAG_NAME_NOT_NULL);
        }

        if (isBlank(update.getName())) {
            throw new TodoException(TAG_NAME_NOT_EMPTY);
        }
    }

    public List<Tag> find() {
        List<TagDocument> documents = tagRepository.findByUsername(userService.getCurrentUsername());
        List<Tag> tags = new ArrayList();
        documents.forEach(document -> tags.add(conversionService.convert(document, Tag.class)));
        return tags;
    }

    public Tag findById(String id) {
        Optional<TagDocument> tag = tagRepository.findByIdAndUsername(id, userService.getCurrentUsername());
        if (!tag.isPresent()) {
            throw new TodoException(INVALID_TAG_ID);
        }
        return conversionService.convert(tag.get(), Tag.class);
    }

    public Tag delete(String id) {
        Optional<TagDocument> deleteTag = tagRepository.deleteByIdAndUsername(id, userService.getCurrentUsername());
        if (!deleteTag.isPresent()) {
            throw new TodoException(INVALID_TAG_ID);
        }
        return conversionService.convert(deleteTag.get(), Tag.class);
    }
}
