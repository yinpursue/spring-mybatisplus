package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * UserController 集成测试
 * 启动完整 Spring 容器，走 Controller → Mapper → 数据库 全链路
 * 数据实际写入测试数据库，测试后清理
 */
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserMapper userMapper;

    @AfterEach
    void cleanUp() {
        // 清理测试数据
        userMapper.delete(null);
    }

    @Test
    void getUser_shouldReturnUser_whenIdExists() throws Exception {
        // 准备数据，实际写入数据库
        User user = new User();
        user.setName("张三");
        userMapper.insert(user);

        // 通过 MockMvc 发起请求，走完整链路
        mockMvc.perform(get("/user/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.name").value("张三"));
    }

    @Test
    void getUser_shouldReturnNull_whenIdNotExists() throws Exception {
        mockMvc.perform(get("/user/99999"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}
