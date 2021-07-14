package com.internship.sbproject1.dto;


import com.internship.sbproject1.entity.UserRole;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
public class RequestUserDto {
    private long id;
    @NotNull
    private String fullName;
    @NotNull
    private String email;
    @NotNull
    private int gender;
    @NotNull
    private UserRole userRole;
    private String password;
}
