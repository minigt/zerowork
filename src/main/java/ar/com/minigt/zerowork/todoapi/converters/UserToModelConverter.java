package ar.com.minigt.zerowork.todoapi.converters;

import ar.com.minigt.zerowork.todoapi.entities.UserDocument;
import ar.com.minigt.zerowork.todoapi.models.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToModelConverter implements Converter<UserDocument, User> {

    @Override
    public User convert(UserDocument document) {
        User user = new User();
        user.setId(document.getId());
        user.setUsername(document.getUsername());
        user.setPasswordHash(document.getPasswordHash());
        return user;
    }
}
