package com.internship.sbproject1.service;

import com.internship.sbproject1.entity.Skill;
import com.internship.sbproject1.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService {
    private SkillRepository skillRepository;

    @Autowired
    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<Skill> getSkills() {
        return (List<Skill>) skillRepository.findAll();
    }

    public Skill getSkillById(long id) {
        Skill s = skillRepository.findById(id).orElseThrow(() -> new IllegalStateException("skill with id: " + id + " was not found"));
        return  s;
    }

    public Skill addNewSkill(Skill skill) {
        Optional<Skill> skillByName = skillRepository.findByName(skill.getName());

        if(skillByName.isPresent()) {
            throw new IllegalStateException("Skill with that name already exists");
        }
        skillRepository.save(skill);
        return skill;
    }

    public Skill updateSkill(Long id, String name, String description) {
        Skill s = skillRepository.findById(id).orElseThrow(() -> new IllegalStateException("skill with id: " + id + " was not found"));
        s.setName(name);
        s.setDescription(description);
        skillRepository.save(s);
        return s;
    }

    public void deleteSkill(Long id) {
        boolean exists = skillRepository.existsById(id);
        if(!exists) {
            throw new IllegalStateException("Skill does not exist!");
        }
        skillRepository.deleteById(id);
    }

}
