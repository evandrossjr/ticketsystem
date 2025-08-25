package com.essj.ticketsystem;


import com.essj.ticketsystem.controller.api.UserController;
import com.essj.ticketsystem.dtos.UserDTO;
import com.essj.ticketsystem.models.enums.UserRole;
import com.essj.ticketsystem.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class) // Aponta para a classe que será testada
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean // Cria um mock do UserService no contexto de teste
    private UserService userService;

    @Test
    public void testGetAllUsers() throws Exception {
        UserDTO user1 = new UserDTO("joao_silva", "joao@email.com", UserRole.USER);
        List<UserDTO> userList = Collections.singletonList(user1);

        // Define o comportamento do mock: quando findAll() for chamado, retorne a lista de DTOs.
        when(userService.findAll()).thenReturn(userList);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].username").value("joao_silva"));
    }

    @Test
    public void testCreateUser() throws Exception {
        UserDTO newUserDTO = new UserDTO("maria_santos", "maria@email.com", UserRole.ADMIN);

        // Define o comportamento do mock: quando save() for chamado com qualquer DTO, retorne o mesmo DTO.
        when(userService.save(newUserDTO)).thenReturn(newUserDTO);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("maria_santos"));
    }
}
