package com.internship.sbproject1.dto;


import com.internship.sbproject1.entity.UserRole;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseUserDto {
    @NotNull
    private Long id;
    @NotNull
    private String fullName;
    @NotNull
    private String email;
    @NotNull
    private UserRole userRole;
    @NotNull
    private int gender;
}
