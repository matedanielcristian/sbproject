package com.internship.sbproject1.controller;

import com.internship.sbproject1.entity.Skill;
import com.internship.sbproject1.service.SkillService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/skills")
public class SkillController {

    public  final SkillService skillService;

    public  SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public List<Skill> getSkills() {
        return skillService.getSkills();
    }

    @GetMapping(path = "{skillId}")
    public Skill getSkills(@PathVariable("skillId") long skillId) {
        return skillService.getSkillById(skillId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Skill addSkill(@RequestBody Skill skill) {
        return skillService.addNewSkill(skill);
    }

    @PutMapping(path = "{skillId}")
    public Skill updateSkill(@PathVariable("skillId") Long skillId, @RequestBody Skill skill) {
        return skillService.updateSkill(skillId, skill.getName(), skill.getDescription());
    }

    @DeleteMapping(path = "{skillId}")
    public void deleteSkill(@PathVariable("skillId") Long skillId) {
        skillService.deleteSkill(skillId);
    }
}
