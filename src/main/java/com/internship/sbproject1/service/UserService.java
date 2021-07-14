package com.internship.sbproject1.service;

import com.internship.sbproject1.dto.RequestUserDto;
import com.internship.sbproject1.entity.Skill;
import com.internship.sbproject1.entity.User;
import com.internship.sbproject1.dto.ResponseUserDto;
import com.internship.sbproject1.entity.UserSkill;
import com.internship.sbproject1.repository.SkillRepository;
import com.internship.sbproject1.repository.UserRepository;
import com.internship.sbproject1.entity.UserRole;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService {

    private UserRepository userRepository;
    private SkillRepository skillRepository;
    private ModelMapper mapper;

    @Autowired
    public UserService(UserRepository userRepository, SkillRepository skillRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
        this.mapper = mapper;
    }

    public ResponseUserDto findUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("User with id " + userId + " was not found"));
        return toResponseDto(user);
    }

    public Object getUsers(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        // TODO: Use response DTO here
        Page<User> pagedResult = userRepository.findAll(paging);
        return pagedResult;
    }

    public ResponseUserDto addNewUser(RequestUserDto userFromRequest) {
        Optional<User> studentByEmail =  userRepository.findUserByEmail(userFromRequest.getEmail());
        if(studentByEmail.isPresent()) {
            throw new IllegalStateException("Your email is already used!");
        }
        User user = this.RequestToEntity(userFromRequest);
        userRepository.save(user);
        return toResponseDto(user);
    }

    public void deleteUser(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if(!exists) {
            throw new IllegalStateException("user with id " + userId + " does not exists");
        }
        userRepository.deleteById(userId);
    }

    public ResponseUserDto updateUser(Long userId, String fullName, String email, int gender, UserRole userRole) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("User with id " + userId + " was not found!"));
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if(userOptional.isPresent() && userOptional.get().getId() != user.getId()) {
            throw  new IllegalStateException("Email already taken!");
        }
        user.setEmail(email);
        user.setFullName(fullName);
        user.setGender(gender);
        user.setUserRole(userRole);
        userRepository.save(user);
        return toResponseDto(user);
    }


    public User addSkillToUser(Long userId, Long skillId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("User with id " + userId + " was not found!"));
        Skill skill = skillRepository.findById(skillId).orElseThrow(() -> new IllegalStateException("Skill with id " + skillId + " was not found!"));
        user.addSkill(skill);
        return user;
    }


    public List<UserSkill> getUserSkills(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("User with id " + userId + " was not found!"));
        return user.getSkills();
    }


    // DTO Response conversions
    private ResponseUserDto toResponseDto(User user) {
        ResponseUserDto responseUserDto = mapper.map(user, ResponseUserDto.class);
        return responseUserDto;
    }

    private List<ResponseUserDto> ListToResponseListDto(List<User> users) {
        List<ResponseUserDto> dtos = users.stream().map(user -> mapper.map(user, ResponseUserDto.class )).collect(Collectors.toList());
        return dtos;
    }

    private User ResponseToEntity(ResponseUserDto responseUserDto) {
        User user =  mapper.map(responseUserDto, User.class);
        return user;
    }


    // DTO Request conversions
    private RequestUserDto toRequestDto(User user) {
        RequestUserDto resUserDto = mapper.map(user, RequestUserDto.class);
        return resUserDto;
    }

    private List<RequestUserDto> ListToRequestListDto(List<User> users) {
        List<RequestUserDto> dtos = users.stream().map(user -> mapper.map(user, RequestUserDto.class )).collect(Collectors.toList());
        return dtos;
    }

    private User RequestToEntity(RequestUserDto requestUserDto) {
        User user =  mapper.map(requestUserDto, User.class);
        return user;
    }

    

}
