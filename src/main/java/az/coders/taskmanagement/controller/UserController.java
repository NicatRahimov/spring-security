package az.coders.taskmanagement.controller;

import az.coders.taskmanagement.entities.User;
import az.coders.taskmanagement.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

   private final UserRepository userRepository;

    @GetMapping("/getAll")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
