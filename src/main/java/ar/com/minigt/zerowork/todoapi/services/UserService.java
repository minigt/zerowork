package ar.com.minigt.zerowork.todoapi.services;

import static ar.com.minigt.zerowork.todoapi.exceptions.TodoErrorCode.*;
import static java.lang.String.*;
import static org.apache.commons.lang3.StringUtils.*;

import ar.com.minigt.zerowork.todoapi.entities.UserDocument;
import ar.com.minigt.zerowork.todoapi.exceptions.TodoException;
import ar.com.minigt.zerowork.todoapi.models.User;
import ar.com.minigt.zerowork.todoapi.repositories.UserRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ConversionService conversionService;
    private final HttpServletRequest request;
    private static final Set<String> tokens = new HashSet<>();
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository,
                       ConversionService conversionService,
                       HttpServletRequest request) {
        this.userRepository = userRepository;
        this.conversionService = conversionService;
        this.request = request;
    }

    public User getUserByUsername(String email) {
        return conversionService.convert(userRepository.findByUsername(email)
            .orElseThrow(() -> new TodoException(USERNAME_DOESNT_EXISTS)), User.class);
    }

    public User register(User user) {
        validateUserCreation(user);
        UserDocument userDocument = new UserDocument();
        userDocument.setUsername(user.getUsername());
        userDocument.setPasswordHash(encrypt(user.getPassword()));
        return conversionService.convert(userRepository.save(userDocument), User.class);
    }

    private void validateUserCreation(User user) {
        if (isBlank(user.getUsername())) {
            throw new TodoException(USERNAME_MUST_BE_SET);
        }
        if (isBlank(user.getPassword())) {
            throw new TodoException(PASSWORD_MUST_BE_SET);
        }
        if (!user.getPassword().equals(user.getPasswordRepeated())) {
            throw new TodoException(PASSWORD_DONT_MATCH);
        }
        Optional<UserDocument> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new TodoException(USERNAME_ALREADY_EXISTS);
        }
    }

    public static String encrypt(String password) {
        return encoder.encode(password);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return new ar.com.minigt.zerowork.todoapi.models.UserDetails(getUserByUsername(username));
        } catch (Throwable e) {
            throw new UsernameNotFoundException(format("User with username=%s was not found", username));
        }
    }

    public boolean canAccessUser(String username) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User currentUser =
            (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        return currentUser != null && currentUser.getUsername().equals(username);
    }

    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();
    }

}