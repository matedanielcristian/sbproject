package com.internship.sbproject1.controller;

import com.internship.sbproject1.dto.RequestUserDto;
import com.internship.sbproject1.dto.ResponseUserDto;
import com.internship.sbproject1.entity.User;
import com.internship.sbproject1.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
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
    public ResponseEntity<Page<User>> getUsers(@RequestParam(defaultValue = "0") @Valid Integer pageNo,
                                               @RequestParam(defaultValue = "10") @Valid Integer pageSize,
                                               @RequestParam(defaultValue = "id") @Valid String sortBy
    ) {
        Page<User> result =  userService.getUsers(pageNo, pageSize, sortBy);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "{userId}")
    public  ResponseEntity<ResponseUserDto> getUserById(@PathVariable("userId") @Valid Long userId) {
        ResponseUserDto result = userService.findUserById(userId);
        return  ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<ResponseUserDto> saveUser(@RequestBody @Valid RequestUserDto user) {
        ResponseUserDto result = userService.saveUser(user);
        return ResponseEntity.ok(result);
    }

    @PutMapping(path = "{userId}")
    public ResponseEntity<ResponseUserDto>updateUser(@PathVariable("userId") @Valid Long userId, @RequestBody @Valid RequestUserDto userToUpdate) {
        ResponseUserDto result  = userService.updateUser(userId,  userToUpdate);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(path = "{userId}")
    public ResponseEntity<Boolean> deleteStudent(@PathVariable("userId") @Valid Long userId) {
        Boolean result = userService.deleteUser(userId);
        return ResponseEntity.ok(result);
    }
}
