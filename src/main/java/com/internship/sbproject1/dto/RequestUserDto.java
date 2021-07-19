package com.internship.sbproject1.dto;


import com.internship.sbproject1.entity.UserRole;
import com.sun.istack.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestUserDto {
    private Long id;
    @NotNull
    private String fullName;
    @NotNull
    private String email;
    @NotNull
    private UserRole userRole;
    @NotNull
    private int gender;
    private String password;
}
