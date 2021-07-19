package com.internship.sbproject1.service;

import com.internship.sbproject1.dto.RequestUserDto;
import com.internship.sbproject1.entity.User;
import com.internship.sbproject1.dto.ResponseUserDto;
import com.internship.sbproject1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public ResponseUserDto findUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("User with id " + userId + " was not found"));
        return toResponseDto(user);
    }

    public Page<User> getUsers(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        // TODO: Use response DTO here
        return userRepository.findAll(paging);
    }

    public ResponseUserDto saveUser(RequestUserDto userFromRequest) {
        Optional<User> studentByEmail =  userRepository.findUserByEmail(userFromRequest.getEmail());
        if(studentByEmail.isPresent()) {
            throw new IllegalStateException("Your email is already used!");
        }
        User user = this.RequestToEntity(userFromRequest);
        userRepository.save(user);
        return toResponseDto(user);
    }

    public boolean deleteUser(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if(!exists) {
            throw new IllegalStateException("user with id " + userId + " does not exists");
        }
        userRepository.deleteById(userId);
        return true;
    }

    public ResponseUserDto updateUser(Long userId, RequestUserDto userToUpdate) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("User with id " + userId + " was not found!"));
        Optional<User> userOptional = userRepository.findUserByEmail(userToUpdate.getEmail());
        if(userOptional.isPresent() && userOptional.get().getId() != user.getId()) {
            throw  new IllegalStateException("Email already taken!");
        }
        user.setEmail(userToUpdate.getEmail());
        user.setFullName(userToUpdate.getFullName());
        user.setGender(userToUpdate.getGender());
        user.setUserRole(userToUpdate.getUserRole());
        userRepository.save(user);
        return toResponseDto(user);
    }

    // DTO Response conversions
    public ResponseUserDto toResponseDto(User user) {
        ResponseUserDto responseUserDto = mapper.map(user, ResponseUserDto.class);
        return responseUserDto;
    }

    public User ResponseToEntity(ResponseUserDto responseUserDto) {
        User user =  mapper.map(responseUserDto, User.class);
        return user;
    }

    public RequestUserDto toRequestDto(User user) {
        RequestUserDto resUserDto = mapper.map(user, RequestUserDto.class);
        return resUserDto;
    }

    public User RequestToEntity(RequestUserDto requestUserDto) {
        User user =  mapper.map(requestUserDto, User.class);
        return user;
    }

    

}
