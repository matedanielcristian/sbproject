package com.internship.sbproject1.controller;

import com.internship.sbproject1.dto.RequestUserDto;
import com.internship.sbproject1.dto.ResponseUserDto;
import com.internship.sbproject1.entity.User;
import com.internship.sbproject1.entity.UserSkill;
import com.internship.sbproject1.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {
    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Object getUsers(@RequestParam(defaultValue = "0") Integer page,
                         @RequestParam(defaultValue = "10") Integer size,
                         @RequestParam(defaultValue = "id") String sortOrder
    ) {
        return userService.getUsers(page, size, sortOrder);
    }

    @GetMapping(path = "{userId}")
    public ResponseUserDto getUserById(@PathVariable("userId") Long userId) {
        return  userService.findUserById(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseUserDto registerNewUser(@RequestBody @Validated RequestUserDto user) {
        return userService.addNewUser(user);
    }


    @PutMapping(path = "{userId}")
    public ResponseUserDto updateStudent(@PathVariable("userId") Long userId, @RequestBody @Validated RequestUserDto userToUpdate) {
        return userService.updateUser(userId,  userToUpdate.getFullName(),  userToUpdate.getEmail(), userToUpdate.getGender(), userToUpdate.getUserRole());
    }


    @GetMapping(path = "{userId}/skills")
    public List<UserSkill> getUserSkills(@PathVariable("userId") Long userId) {
        return userService.getUserSkills(userId);
    }

    @PostMapping(path = "{userId}/addskill/{skillId}")
    public User addSkillToUser(@PathVariable("userId") Long userId, @PathVariable("userId") Long skillId) {
        return  userService.addSkillToUser(userId, skillId);
    }

    @DeleteMapping(path = "{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteStudent(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
    }
}
