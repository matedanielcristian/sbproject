package com.internship.sbproject1;

import com.internship.sbproject1.dto.RequestUserDto;
import com.internship.sbproject1.dto.ResponseUserDto;
import com.internship.sbproject1.entity.User;
import com.internship.sbproject1.entity.UserRole;

public abstract class UsersBase {
    public Long id = 1L;
    public Integer gender = 1;
    public RequestUserDto requestUserDto;

    {
        requestUserDto = new RequestUserDto(id, "Mate Daniel", "test@test.com", gender, UserRole.USER, "123456");
    }

    public User user;

    {
        user = new User(id, requestUserDto.getFullName(), requestUserDto.getEmail(), requestUserDto.getPassword(), requestUserDto.getUserRole(), requestUserDto.getGender());
    }

    public ResponseUserDto responseUserDto;

    {
        responseUserDto = new ResponseUserDto(id, user.getFullName(), user.getEmail(), user.getGender(), user.getUserRole());
    }

}
