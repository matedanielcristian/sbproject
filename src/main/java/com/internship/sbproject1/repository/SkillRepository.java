package com.internship.sbproject1.repository;

import com.internship.sbproject1.entity.Skill;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface SkillRepository extends PagingAndSortingRepository<Skill, Long> {
    Optional<Skill> findByName(String name);
}
