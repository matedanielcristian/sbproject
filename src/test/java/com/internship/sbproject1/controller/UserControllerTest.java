package com.internship.sbproject1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.internship.sbproject1.TestCreationFactory;
import com.internship.sbproject1.dto.RequestUserDto;
import com.internship.sbproject1.dto.ResponseUserDto;
import com.internship.sbproject1.entity.User;
import com.internship.sbproject1.repository.UserRepository;
import com.internship.sbproject1.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@EnableSpringDataWebSupport
public class UserControllerTest extends TestCreationFactory {
    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();
    private String USERs_URL = "/api/v1/users";

    public User user = this.getUser();
    public ResponseUserDto responseUserDto = this.getResponseUserDto();
    public RequestUserDto requestUserDto = this.getRequestUserDto();

    @Test
    public void getUsersTest() throws Exception {
        User user = this.getUser();
        Integer pageNo = 0;
        Integer pageSize = 10;
        String sortBy = "id";
        Page<User> page = new PageImpl<>(Collections.singletonList(user));
        given(userService.getUsers(pageNo, pageSize, sortBy)).willReturn(page);
        mockMvc.perform(get(USERs_URL)
                .param("pageNo", pageNo.toString())
                .param("pageSize", pageSize.toString())
                .param("sortBy", sortBy)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].fullName", is(user.getFullName())));
        verify(userService, times(1)).getUsers(pageNo, pageSize, sortBy);
    }


    @Test
    public void getUserByIdTest() throws Exception {
        long id = 1;
        when(userService.findUserById(responseUserDto.getId())).thenReturn(responseUserDto);
        mockMvc.perform(get(USERs_URL + "/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(responseUserDto), true));
        verify(userService, times(1)).findUserById(user.getId());
    }

    @Test
    public void saveUserTest() throws Exception {
        long id = 1;
        when(userService.saveUser(requestUserDto)).thenReturn(responseUserDto);
        mockMvc.perform(post(USERs_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestUserDto)))
                .andExpect(status().isOk());
        verify(userService, times(1)).saveUser(requestUserDto);
    }

    @Test
    public void updateUserTest() throws Exception {
        requestUserDto.setFullName("User fullName");
        responseUserDto.setFullName("User fullName");
        when(userService
                .updateUser(requestUserDto.getId(), requestUserDto))
                .thenReturn(responseUserDto);
        ResultActions result = mockMvc.perform(put(USERs_URL + "/" + requestUserDto.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestUserDto)))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
        ResultActions json = result.andExpect(content().json(new ObjectMapper().writeValueAsString(responseUserDto), true));
        verify(userService, times(1)).updateUser(requestUserDto.getId(), requestUserDto);
    }

    @Test
    public void deleteUserTest() throws Exception {
        when(userService.deleteUser(requestUserDto.getId())).thenReturn(true);
        ResultActions result = mockMvc.perform(delete(USERs_URL + "/" + requestUserDto.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestUserDto)))
                .andExpect(status().isOk());
        verify(userService, times(1)).deleteUser(requestUserDto.getId());
    }
}