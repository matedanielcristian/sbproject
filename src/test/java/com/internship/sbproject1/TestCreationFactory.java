package com.internship.sbproject1;

import com.internship.sbproject1.dto.RequestUserDto;
import com.internship.sbproject1.dto.ResponseUserDto;
import com.internship.sbproject1.entity.User;
import com.internship.sbproject1.entity.UserRole;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;
public abstract class TestCreationFactory {
    private Random random = new Random();
    private Long id =  1L;
    private String email = "test@test.com";
    private String fullName = "Test FullName";
    private UserRole userRole = UserRole.USER;
    private int gender = 1;
    private String password = "123456";


    public ResponseUserDto getResponseUserDto() {
        return new ResponseUserDto(id, fullName, email, userRole, gender);
    }

    public RequestUserDto getRequestUserDto() {
        return new RequestUserDto(id, fullName, email, UserRole.USER, 1, password);
    }

    public User getUser() {
        return new User(id, fullName, email,userRole, 1, password);
    }


    public ResponseUserDto randomResponseUserDto() {
        return new ResponseUserDto((long) random.nextInt(), RandomStringUtils.randomAlphabetic(10) , RandomStringUtils.randomAlphabetic(10), UserRole.USER, random.nextInt(2));
    }

    public RequestUserDto randomRequestUserDto() {
        return new RequestUserDto((long) random.nextInt(), RandomStringUtils.randomAlphabetic(10) , RandomStringUtils.randomAlphabetic(10), UserRole.USER, random.nextInt(2), RandomStringUtils.randomAlphabetic(10));
    }

    public User randomUser() {
        return new User((long) random.nextInt(), RandomStringUtils.randomAlphabetic(10) , RandomStringUtils.randomAlphabetic(10), UserRole.USER, random.nextInt(2), RandomStringUtils.randomAlphabetic(10));
    }



}
