package com.internship.sbproject1.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.internship.sbproject1.entity.Skill;
import com.internship.sbproject1.repository.SkillRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileReader;
import java.util.Iterator;

@Configuration
public class SkillConfig {
    @Bean
    CommandLineRunner clr(
            SkillRepository skillRepository
    ) {
        JSONParser parser = new JSONParser();
        try {
            String name = new File(".").getCanonicalPath();
            System.out.println("Test "+ name);
            File file = new File("/home/danielmate/Desktop/JavaTutorials/eskills/src/main/java/com/internship/sbproject1/config/skills.json");
            Object obj = parser.parse(new FileReader(file));

            // A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
            JSONObject jsonObject = (JSONObject) obj;

            // A JSON array. JSONObject supports java.util.List interface.
            JSONArray skillsList = (JSONArray)  jsonObject.get("skills");

            // An iterator over a collection. Iterator takes the place of Enumeration in the Java Collections Framework.
            // Iterators differ from enumerations in two ways:
            // 1. Iterators allow the caller to remove elements from the underlying collection during the iteration with well-defined semantics.
            // 2. Method names have been improved.
            Iterator<JSONObject> iterator = skillsList.iterator();
            while (iterator.hasNext()) {
                ObjectMapper objectMapper = new ObjectMapper();
                Skill t = objectMapper.readValue(iterator.next().toJSONString(), Skill.class);
                skillRepository.save(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return args -> {

        };
    }
}
