package com.internship.sbproject1.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter
@Setter
@Builder
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

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private UserRole userRole;

    @Column(nullable = false)
    private int gender;


    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UserSkill> skills = new ArrayList<>();

    public void addSkill(Skill skill) {
        UserSkill userSkill = new UserSkill(this, skill);
        skills.add(userSkill);
        skill.getUsers().add(userSkill);
    }

    public void removeSkill(Skill skill) {
        for (Iterator<UserSkill> iterator = skills.iterator();
             iterator.hasNext(); ) {
            UserSkill userSkill = iterator.next();

            if (userSkill.getUser().equals(this) &&
                    userSkill.getSkill().equals(skill)) {
                iterator.remove();
                userSkill.getSkill().getUsers().remove(userSkill);
                userSkill.setUser(null);
                userSkill.setSkill(null);
            }
        }
    }



}
