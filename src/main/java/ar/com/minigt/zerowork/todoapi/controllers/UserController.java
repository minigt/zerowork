package ar.com.minigt.zerowork.todoapi.controllers;

import ar.com.minigt.zerowork.todoapi.models.User;
import ar.com.minigt.zerowork.todoapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    private User register(@RequestBody User user) {
        return userService.register(user);
    }

}