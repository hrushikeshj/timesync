package com.timesync.authentication.User;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*return List.of(
                new User(10L, "f", "d", "f"),
                new User(10L, "10", "d", "f")
        );*/
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public Optional<User> findUser(Long id){
        return userRepository.findById(id);
    }

    public void addNewUser(User user){
        user.generatePasswordDigest();
        System.out.println(user);
        System.out.println(user.verify(user._getPassword()));

        Optional<User> a = userRepository.findFirstByEmail(user.getEmail());
        if(a.isPresent()){
            throw new IllegalStateException("email taken");
        }

        userRepository.save(user);

    }
}
