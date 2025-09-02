package com.essj.ticketsystem.controller.api;


import com.essj.ticketsystem.dtos.UserDTO;
import com.essj.ticketsystem.exceptions.ResourceNotFoundException;
import com.essj.ticketsystem.exceptions.UsernameAlreadyExistsException;
import com.essj.ticketsystem.models.enums.UserRole;
import com.essj.ticketsystem.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
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
@AutoConfigureMockMvc // Adicione esta linha
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean // Cria um mock do UserService no contexto de teste
    private UserService userService;

    @Test
    @WithMockUser(username = "joao_silva", roles = {"USER"})
    public void testGetAllUsers() throws Exception {
        UserDTO user1 = new UserDTO("joao_silva", "joao@email.com", UserRole.USER);
        List<UserDTO> userList = Collections.singletonList(user1);

        // Obtém o usuário autenticado do contexto de segurança
        UserDetails loggedInUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        // Define o comportamento do mock: quando findAll() for chamado, retorne a lista de DTOs.
        when(userService.findAll(loggedInUser)).thenReturn(userList);

        mockMvc.perform(get("/api/users/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].username").value("joao_silva"));
    }

    @Test
    @WithMockUser(username = "admin_user", roles = {"ADMIN"})
    public void testCreateUser() throws Exception {

        UserDetails loggedInUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();



        UserDTO newUserDTO = new UserDTO("maria_santos", "maria@email.com", UserRole.ADMIN);

        // Define o comportamento do mock: quando save() for chamado com qualquer DTO, retorne o mesmo DTO.
        when(userService.save(newUserDTO, loggedInUser)).thenReturn(newUserDTO);

        mockMvc.perform(post("/api/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("maria_santos"));
    }


    @Test
    @WithMockUser(username = "joao_silva", roles = {"USER"})
    public void testGetUserById() throws Exception {
        UserDTO userDTO = new UserDTO("carlos_moura", "carlos@email.com", UserRole.SUPPORT_AGENT);

        // Obtém o usuário autenticado do contexto de segurança
        UserDetails loggedInUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        when(userService.findById(1L,loggedInUser)).thenReturn(userDTO);
        mockMvc.perform(get("/api/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("carlos_moura"));
    }

    @Test
    @WithMockUser(username = "joao_silva", roles = {"USER"})
    public void testGetUserById_NotFound() throws Exception {

        // Obtém o usuário autenticado do contexto de segurança
        UserDetails loggedInUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        when(userService.findById(999L,loggedInUser)).thenThrow(new ResourceNotFoundException("User not found with id: 999"));

        mockMvc.perform(get("/api/users/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin_user", roles = {"ADMIN"})
    public void testCreateUser_InvalidInput() throws Exception {
        UserDTO invalidUserDTO = new UserDTO("", "invalid-email", null);

        mockMvc.perform(post("/api/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUserDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin_user", roles = {"ADMIN"})
    public void testGetAllUsers_EmptyList() throws Exception {

        // Obtém o usuário autenticado do contexto de segurança
        UserDetails loggedInUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        when(userService.findAll(loggedInUser)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/users/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @WithMockUser(username = "admin_user", roles = {"ADMIN"})
    public void testCreateUser_DuplicateUsername() throws Exception {

        UserDetails loggedInUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserDTO userDTO = new UserDTO("existing_user", "email@email.com", UserRole.USER);

        when(userService.save(userDTO,loggedInUser)).thenThrow(new UsernameAlreadyExistsException("Username already exists"));
        mockMvc.perform(post("/api/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "joao_silva", roles = {"USER"})
    public void testGetUserById_InvalidId() throws Exception {
        mockMvc.perform(get("/api/users/{id}", "invalid-id"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin_user", roles = {"ADMIN"})
    public void testCreateUser_MissingFields() throws Exception {

        UserDTO incompleteUserDTO = new UserDTO(null, "", null);

        mockMvc.perform(post("/api/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incompleteUserDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin_user", roles = {"ADMIN"})
    public void testGetAllUsers_InternalServerError() throws Exception {

        UserDetails loggedInUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        when(userService.findAll(loggedInUser)).thenThrow(new RuntimeException("Database connection error"));

        mockMvc.perform(get("/api/users/"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(username = "admin_user", roles = {"ADMIN"})
    public void testCreateUser_InternalServerError() throws Exception {

        UserDetails loggedInUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserDTO userDTO = new UserDTO("new_user", "email@email.com", UserRole.USER);

        when(userService.save(userDTO,loggedInUser)).thenThrow(new RuntimeException("Database connection error"));
        mockMvc.perform(post("/api/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(username = "admin_user", roles = {"ADMIN"})
    public void testGetUserById_InternalServerError() throws Exception {

        UserDetails loggedInUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        when(userService.findById(1L, loggedInUser)).thenThrow(new RuntimeException("Database connection error"));

        mockMvc.perform(get("/api/users/{id}", 1L))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(username = "admin_user", roles = {"ADMIN"})
    public void testCreateUser_EmptyBody() throws Exception {
        mockMvc.perform(post("/api/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin_user", roles = {"ADMIN"})
    public void testGetAllUsers_NullResponse() throws Exception {

        UserDetails loggedInUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        when(userService.findAll(loggedInUser)).thenReturn(null);

        mockMvc.perform(get("/api/users/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(username = "admin_user", roles = {"ADMIN"})
    public void testCreateUser_NullInput() throws Exception {

        UserDetails loggedInUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        when(userService.save(null,loggedInUser)).thenThrow(new RuntimeException("Input cannot be null"));

        mockMvc.perform(post("/api/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }

}
