package ar.com.minigt.zerowork.todoapi.models;

import static org.springframework.security.core.authority.AuthorityUtils.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetails extends org.springframework.security.core.userdetails.User {

    private User user;

    public UserDetails(User user) {
        super(user.getUsername(), user.getPasswordHash(), createAuthorityList());
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}