package com.timesync.authentication;

import com.timesync.authentication.User.User;
import com.timesync.authentication.User.UserRepository;
import com.timesync.authentication.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(UserService userService, UserRepository userRepository, AuthenticationService authenticationService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    @PostMapping("verify_jwt")
    public User verifyJWT(@RequestBody Map<String, String> req){
        Map<String, Object> map = new HashMap<>();
        System.out.println(req.get("jwt"));


        return authenticationService.verifyJWT(req.get("jwt")); //TODO: error handling
    }

    @PostMapping("generate_token")
    public Map<String, Object> generateToken(@RequestBody User u){
        Map<String, Object> map = new HashMap<>();

        Optional<User> user = userRepository.findFirstByEmail(u.getEmail());
        if(user.isEmpty()){
            map.put("error", "user not found");
            return map;
        }
        if(user.get().verify(u._getPassword())){
            String jwt = authenticationService.generateJWT(user.get());
            map.put("jwt", jwt);
        }
        else {
            map.put("error", "wrong password");
        }
        System.out.println(user);
        return map;
    }
}
