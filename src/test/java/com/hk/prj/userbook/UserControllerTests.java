package com.hk.prj.userbook;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hk.prj.userbook.user.User;
import com.hk.prj.userbook.user.UserController;
import com.hk.prj.userbook.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ExtendWith(SpringExtension.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void getAllUsers_Test() throws Exception {
        when(userService.getUsers()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/users")).andExpect(status().is2xxSuccessful());
    }

    @Test
    void postUsers_Test() throws Exception {
        when(userService.saveUser(any(User.class))).thenReturn(new User(1L, "Hemant", "Kumar"));
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new User(1L, "Hemant", "Kumar"))))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
