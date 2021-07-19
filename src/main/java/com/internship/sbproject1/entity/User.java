package com.internship.sbproject1.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode

@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "User")
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(name = "user_email_unique", columnNames = "email")
})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class User {
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    @NotNull
    private String email;
    
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private UserRole userRole;

    @Column(nullable = false)
    private int gender;

    @Column(nullable = false)
    private String password;




}
