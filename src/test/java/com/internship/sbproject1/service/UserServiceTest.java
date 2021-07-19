package com.internship.sbproject1.service;

import com.internship.sbproject1.TestCreationFactory;
import com.internship.sbproject1.dto.RequestUserDto;
import com.internship.sbproject1.dto.ResponseUserDto;
import com.internship.sbproject1.entity.User;
import com.internship.sbproject1.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest extends TestCreationFactory {

    @InjectMocks
    public UserService userService;

    @Mock
    public ModelMapper mapper;

    @Mock
    public UserRepository userRepository;

    public User user = this.getUser();
    public ResponseUserDto responseUserDto = this.getResponseUserDto();
    public RequestUserDto requestUserDto = this.getRequestUserDto();



    @Test
    public void findUserByIdTest() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        lenient().when(userService.toResponseDto(user)).thenReturn(responseUserDto);
        ResponseUserDto responseUserDto = userService.findUserById(user.getId());
        assertEquals(responseUserDto.getEmail(), user.getEmail());
    }

    @Test(expected = IllegalStateException.class)
    public void findUserByIdTestExpectIllegalStateException() {
        when(userRepository.findById(user.getId())).thenThrow(new IllegalStateException("user was not found by id"));
        lenient().when(userService.toResponseDto(user)).thenReturn(responseUserDto);
        ResponseUserDto responseUserDto = userService.findUserById(user.getId());
    }

    @Test
    public  void getUsersTest() {
        int pageNo = 1;
        int pageSize = 10;
        String sortBy = "id";
        User user1 = this.randomUser();
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<User> userPage = new PageImpl<User>(Collections.singletonList(user1));
        when(userRepository.findAll(pageable)).thenReturn(userPage);
        Page<User> userPageResult = userService.getUsers(pageNo, pageSize, sortBy);
        assertEquals(userPageResult.getTotalElements(), 1);
    }

    @Test
    public  void saveUserTest() {
        RequestUserDto requestUserDto = this.getRequestUserDto();
        User user = this.getUser();
        ResponseUserDto responseUserDto = this.getResponseUserDto();
        when(userRepository.findUserByEmail(requestUserDto.getEmail())).thenReturn(Optional.empty());
        lenient().when(userService.toResponseDto(user)).thenReturn(responseUserDto);
        lenient().when(userService.RequestToEntity(requestUserDto)).thenReturn(user);
        lenient().when(userRepository.save(any())).thenReturn(user);

        ResponseUserDto createdUser = userService.saveUser(requestUserDto);
        assertEquals(createdUser, responseUserDto);
        assertEquals(createdUser.getEmail(), responseUserDto.getEmail());
    }

    @Test(expected = IllegalStateException.class)
    public  void saveUserIllegalStateExceptionTeset() {
        RequestUserDto requestUserDto = this.getRequestUserDto();
        User user = this.getUser();
        User user2 = this.randomUser();
        user2.setEmail(user.getEmail());
        ResponseUserDto responseUserDto = this.getResponseUserDto();
        when(userRepository.findUserByEmail(requestUserDto.getEmail())).thenReturn(Optional.of(user2));
        lenient().when(userService.toResponseDto(user)).thenReturn(responseUserDto);
        lenient().when(userService.RequestToEntity(requestUserDto)).thenReturn(user);
        lenient().when(userRepository.save(any())).thenReturn(user);
        ResponseUserDto createdUser = userService.saveUser(requestUserDto);
    }


    @Test
    public  void deleteUserTest() {
        when(userRepository.existsById(user.getId())).thenReturn(true);
        userService.deleteUser(user.getId());
        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    public void updateUserTest() {
        when(userRepository.save(any())).thenReturn(user);
        lenient().when(userRepository.findById(requestUserDto.getId())).thenReturn(Optional.of(user));
        lenient().when(userRepository.findUserByEmail(requestUserDto.getEmail())).thenReturn(Optional.of(user));
        lenient().when(userService.toResponseDto(user)).thenReturn(responseUserDto);
        lenient().when(userService.updateUser(requestUserDto.getId(), requestUserDto)).thenReturn(responseUserDto);

        ResponseUserDto editedUser = userService.updateUser(requestUserDto.getId(), requestUserDto);
        Assertions.assertEquals(editedUser.getId(), user.getId());
    }

    @Test(expected = IllegalStateException.class)
    public void deleteUserIllegalStateExceptionTest() {
        User newUser1 = this.getUser();
        newUser1.setId(2L);
        when(userRepository.existsById(newUser1.getId())).thenReturn(false);
        boolean deleteUser = userService.deleteUser(newUser1.getId());
    }


    @Test
    public void toResponseDtoTest() {
        when(userService.toResponseDto(user)).thenReturn(responseUserDto);
        Assertions.assertEquals(user.getId(), responseUserDto.getId());
        Assertions.assertEquals(user.getEmail(), responseUserDto.getEmail());
        Assertions.assertEquals(user.getUserRole(), responseUserDto.getUserRole());
    }


    @Test
    public void responseToEntityTest() {
        when(userService.ResponseToEntity(responseUserDto)).thenReturn(user);
        User u2 = userService.ResponseToEntity(responseUserDto);
        Assertions.assertEquals(u2.getId(), responseUserDto.getId());
    }

    @Test
    public void toRequestDtoTest() {
        when(userService.toRequestDto(user)).thenReturn(requestUserDto);
        requestUserDto = userService.toRequestDto(user);
        Assertions.assertEquals(user.getId(), requestUserDto.getId());
        Assertions.assertEquals(user.getEmail(), requestUserDto.getEmail());
        Assertions.assertEquals(user.getUserRole(), requestUserDto.getUserRole());
    }


    @Test
    public void requestToEntityTest() {
        when(userService.RequestToEntity(requestUserDto)).thenReturn(user);
        user = userService.RequestToEntity(requestUserDto);
        Assertions.assertEquals(user.getEmail(), requestUserDto.getEmail());
        Assertions.assertEquals(user.getUserRole(), requestUserDto.getUserRole());
    }
}