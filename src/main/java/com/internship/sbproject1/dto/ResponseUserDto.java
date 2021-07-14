package com.internship.sbproject1.dto;


import com.internship.sbproject1.entity.UserRole;
import lombok.Data;

@Data
public class ResponseUserDto {
    private long id;
    private String fullName;
    private String email;
    private int gender;
    private UserRole userRole;
}
