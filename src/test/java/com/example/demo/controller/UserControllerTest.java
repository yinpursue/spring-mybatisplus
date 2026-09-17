package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserMapper userMapper;

    @Test
    void getUser_shouldReturnUser_whenIdExists() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("张三");

        when(userMapper.findById(1L)).thenReturn(user);

        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("张三"));
    }

    @Test
    void getUser_shouldReturnNull_whenIdNotExists() throws Exception {
        when(userMapper.findById(999L)).thenReturn(null);

        mockMvc.perform(get("/user/999"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}
