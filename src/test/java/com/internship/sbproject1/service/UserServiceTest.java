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

import javax.jws.soap.SOAPBinding;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertThat;
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
        when(userService.toResponseDto(user)).thenReturn(responseUserDto);
        ResponseUserDto responseUserDto = userService.findUserById(user.getId());
        assertEquals(responseUserDto.getEmail(), user.getEmail());
    }

    @Test(expected = IllegalStateException.class)
    public void findUserByIdTestExpectIllegalStateException() {
        when(userRepository.findById(user.getId())).thenThrow(new IllegalStateException("user was not found by id"));
        when(userService.toResponseDto(user)).thenReturn(responseUserDto);
        ResponseUserDto responseUserDto = userService.findUserById(user.getId());
    }

    @Test
    public void getUsersTest() {
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
    public void saveUserTest() {
        RequestUserDto requestUserDto = this.getRequestUserDto();
        User user = this.getUser();
        ResponseUserDto responseUserDto = this.getResponseUserDto();
        when(userRepository.findUserByEmail(requestUserDto.getEmail())).thenReturn(Optional.empty());
        when(userService.toResponseDto(user)).thenReturn(responseUserDto);
        when(userService.RequestToEntity(requestUserDto)).thenReturn(user);

        ResponseUserDto createdUser = userService.saveUser(requestUserDto);
        assertEquals(createdUser, responseUserDto);
        assertEquals(createdUser.getEmail(), responseUserDto.getEmail());
    }

    @Test(expected = IllegalStateException.class)
    public void saveUserIllegalStateExceptionTest() {
        RequestUserDto requestUserDto = this.getRequestUserDto();
        User user = this.getUser();
        User user2 = this.randomUser();
        user2.setEmail(user.getEmail());
        ResponseUserDto responseUserDto = this.getResponseUserDto();
        when(userRepository.findUserByEmail(requestUserDto.getEmail())).thenReturn(Optional.of(user2));
        when(userService.toResponseDto(user)).thenReturn(responseUserDto);
        when(userService.RequestToEntity(requestUserDto)).thenReturn(user);
        ResponseUserDto createdUser = userService.saveUser(requestUserDto);
    }

    @Test
    public void deleteUserTest() {
        when(userRepository.existsById(user.getId())).thenReturn(true);
        userService.deleteUser(user.getId());
        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    public void updateUserTest() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findUserByEmail(requestUserDto.getEmail())).thenReturn(Optional.of(user));
        when(userRepository.findById(requestUserDto.getId())).thenReturn(Optional.of(user));
        when(userService.toResponseDto(user)).thenReturn(responseUserDto);

        ResponseUserDto editedUser = userService.updateUser(requestUserDto.getId(), requestUserDto);
        Assertions.assertEquals(editedUser.getId(), requestUserDto.getId());
        Assertions.assertEquals(editedUser.getEmail(), requestUserDto.getEmail());
        Assertions.assertEquals(editedUser.getFullName(), requestUserDto.getFullName());
        Assertions.assertEquals(editedUser.getUserRole(), requestUserDto.getUserRole());
    }

    @Test(expected = IllegalStateException.class)
    public void updateUserIllegalStateExceptionTest() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        ResponseUserDto editedUser = userService.updateUser(requestUserDto.getId(), requestUserDto);
        Assertions.assertEquals(editedUser.getId(), user.getId());
        Assertions.assertEquals(editedUser.getEmail(), user.getEmail());
        Assertions.assertEquals(editedUser.getFullName(), user.getFullName());
    }

    @Test(expected = IllegalStateException.class)
    public void updateUserIllegalStateExceptionEmailUsedTest() {
        User user2 = this.getUser();
        user2.setId(20L);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(userRepository.findUserByEmail(requestUserDto.getEmail())).thenReturn(Optional.of(user2));

        ResponseUserDto editedUser = userService.updateUser(requestUserDto.getId(), requestUserDto);
        Assertions.assertEquals(editedUser.getId(), user.getId());
        Assertions.assertEquals(editedUser.getEmail(), user.getEmail());
        Assertions.assertEquals(editedUser.getFullName(), user.getFullName());
    }

    @Test(expected = IllegalStateException.class)
    public void deleteUserIllegalStateExceptionTest() {
        User newUser1 = this.getUser();
        newUser1.setId(2L);
        when(userRepository.existsById(newUser1.getId())).thenReturn(false);
        userService.deleteUser(newUser1.getId());
    }

    @Test
    public void toResponseDtoTest() {
        when(mapper.map(any(User.class), eq(ResponseUserDto.class))).thenReturn(responseUserDto);
        ResponseUserDto responseUserDto1 = userService.toResponseDto(user);
        Assertions.assertEquals(user.getId(), responseUserDto1.getId());
        Assertions.assertEquals(user.getEmail(), responseUserDto1.getEmail());
        Assertions.assertEquals(user.getUserRole(), responseUserDto1.getUserRole());
    }

    @Test
    public void responseToEntityTest() {
        when(mapper.map(any(ResponseUserDto.class), eq(User.class))).thenReturn(user);
        User u2 = userService.ResponseToEntity(responseUserDto);
        Assertions.assertEquals(u2.getId(), responseUserDto.getId());
    }

    @Test
    public void toRequestDtoTest() {
        when(mapper.map(any(User.class), eq(RequestUserDto.class))).thenReturn(requestUserDto);
        requestUserDto = userService.toRequestDto(user);
        Assertions.assertEquals(user.getId(), requestUserDto.getId());
        Assertions.assertEquals(user.getEmail(), requestUserDto.getEmail());
        Assertions.assertEquals(user.getUserRole(), requestUserDto.getUserRole());
        Assertions.assertEquals(user.getGender(), requestUserDto.getGender());
    }

    @Test
    public void requestToEntityTest() {
        when(mapper.map(any(RequestUserDto.class), eq(User.class))).thenReturn(user);
        user = userService.RequestToEntity(requestUserDto);
        Assertions.assertEquals(user.getEmail(), requestUserDto.getEmail());
        Assertions.assertEquals(user.getUserRole(), requestUserDto.getUserRole());
        Assertions.assertEquals(user.getFullName(), requestUserDto.getFullName());
    }
}