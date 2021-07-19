package com.internship.sbproject1;

import com.internship.sbproject1.dto.RequestUserDto;
import com.internship.sbproject1.dto.ResponseUserDto;
import com.internship.sbproject1.entity.User;
import com.internship.sbproject1.entity.UserRole;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;
public abstract class TestCreationFactory {
    private final Random random = new Random();
    private final Long id =  1L;
    private final String email = "test@test.com";
    private final String fullName = "Test FullName";
    private final UserRole userRole = UserRole.USER;
    private final int gender = 1;
    private final String password = "123456";


    public ResponseUserDto getResponseUserDto() {
        return new ResponseUserDto(id, fullName, email, userRole, gender);
    }

    public RequestUserDto getRequestUserDto() {
        return new RequestUserDto(id, fullName, email, UserRole.USER, gender, password);
    }

    public User getUser() {
        return new User(id, fullName, email,userRole, gender, password);
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
