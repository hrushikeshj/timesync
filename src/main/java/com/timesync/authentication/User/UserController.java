package com.timesync.authentication.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> hello(){
        return userService.getUsers();
    }

    @GetMapping(path = "{id}")
    public User getUser(@PathVariable("id") Long id){
        Optional<User> u = userService.findUser(id);
        return u.get(); // TODO: fix this
    }

    @PostMapping
    public void newUser(@RequestBody User user){
        userService.addNewUser(user);
    }
}
