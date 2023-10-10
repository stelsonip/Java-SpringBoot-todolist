package com.ivan.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    // using spring to start repo life cycle
    @Autowired
    private IUserRepository userRepository;
    
    //http://localhost:8080/users/
    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        // verifying if username already exists
        var user = this.userRepository.findByUsername(userModel.getUsername());

        if (user != null) {
            // Error message
            // Status code 400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already existis");
        }

        var userCreated = this.userRepository.save(userModel);
        // confirming that user was created code 200
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
