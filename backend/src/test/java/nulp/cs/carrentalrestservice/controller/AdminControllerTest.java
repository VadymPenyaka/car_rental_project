package nulp.cs.carrentalrestservice.controller;


import nulp.cs.carrentalrestservice.mapper.AdminMapper;
import nulp.cs.carrentalrestservice.model.AdminDTO;
import nulp.cs.carrentalrestservice.security.CustomUserDetailsService;
import nulp.cs.carrentalrestservice.security.JwtService;
import nulp.cs.carrentalrestservice.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.ResultMatcher.*;

@WebMvcTest(AdminController.class)
class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private AdminService adminService;
    @MockBean
    private AdminMapper adminMapper;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;
    @MockBean
    private AuthenticationManager authenticationManager;

    private AdminDTO mockAdminDTO;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
        mockAdminDTO = new AdminDTO();
        mockAdminDTO.setId(UUID.randomUUID());
        mockAdminDTO.setFirstName("John");
        mockAdminDTO.setLastName("Doe");
    }


    @Test
//    @WithMockUser(username = "user", roles = {""})
    void getAllAdmins() throws Exception {
        when(adminService.getAllAdmins()).thenReturn(Collections.singletonList(mockAdminDTO));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/admins")
                        .with(SecurityMockMvcRequestPostProcessors.user("user").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void createAdmin() {
    }

    @Test
    void getAdminById() throws Exception {
        when(adminService.getAdminById(any())).thenReturn(Optional.of(new AdminDTO()));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/admins/" + UUID.randomUUID())
                        .with(SecurityMockMvcRequestPostProcessors.user("user").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void deleteAdminById() {
    }

    @Test
    void updateAdminById() {
    }

    @Test
    void authenticateAdmin() {
    }
}