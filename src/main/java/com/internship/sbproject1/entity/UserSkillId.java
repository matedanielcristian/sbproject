package com.internship.sbproject1.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class UserSkillId implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "skill_id")
    private Long skillId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        UserSkillId that = (UserSkillId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(skillId, that.skillId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, skillId);
    }

}
