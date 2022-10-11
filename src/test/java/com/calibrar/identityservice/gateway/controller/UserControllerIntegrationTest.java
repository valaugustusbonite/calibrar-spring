package com.calibrar.identityservice.gateway.controller;

import com.calibrar.identityservice.common.enums.UserStatus;
import com.calibrar.identityservice.domain.dto.UserDto;
import com.calibrar.identityservice.domain.entity.User;
import com.calibrar.identityservice.gateway.database.UserGatewayImpl;
import com.calibrar.identityservice.utils.CustomFormatter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.ResultActions.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    private final static String baseUrl = "/api/v1/user";
    private final static Long inputId = Long.valueOf(1);

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private UserGatewayImpl userGatewayImpl;

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .build();
        bCryptPasswordEncoder = new BCryptPasswordEncoder();

    }


    @Test
    void createUser() throws Exception {
        HashMap<String, Object> inputJson = new HashMap<String, Object>();
        inputJson.put("email", "test@gmail.com");
        inputJson.put("firstName", "firstName");
        inputJson.put("middleName", "lastName");
        inputJson.put("lastName", "lastName");
        inputJson.put("birthDate", "1998-09-01");
        inputJson.put("password", "password");

        UserDto userDto = UserDto
                .builder()
                .email("test@gmail.com")
                .firstName("first")
                .middleName("middle")
                .lastName("last")
                .birthDate(
                        LocalDate.of(1995, 01, 8)
                )
                .password("password")
                .build();

        Mockito.when(userGatewayImpl.createUser(any(UserDto.class))).thenReturn(userDto);

        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl +"/register").contentType(MediaType.APPLICATION_JSON)
            .content(CustomFormatter.convertObjectToJson(inputJson)).characterEncoding("utf-8"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isCreated());


        UserDto userRes = userGatewayImpl.createUser(userDto);

        // then
        assertThat(userRes.getFirstName()).isEqualTo(userDto.getFirstName());
    }

    @Test
    void getUser() throws Exception {
        // given
        UserDto userDto = UserDto
                .builder()
                .email("test@gmail.com")
                .firstName("first")
                .middleName("middle")
                .lastName("last")
                .birthDate(
                        LocalDate.of(1995, 01, 8)
                )
                .password(bCryptPasswordEncoder.encode("password"))
                .build();

        // when
        Mockito.when(userGatewayImpl.getUser(inputId)).thenReturn(userDto);

        mockMvc
                .perform(MockMvcRequestBuilders.get(baseUrl + "/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        UserDto userRes = userGatewayImpl.getUser(inputId);

        // then
        assertThat(userRes.getFirstName()).isEqualTo(userDto.getFirstName());

    }

    @Test
    void deleteUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.patch(baseUrl + "/delete/{id}", inputId)
                .param("id",  "1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        final String success = "Test User has been successfully deleted";

        Mockito.when(userGatewayImpl.deleteUser(any(Long.class))).thenReturn(success);
        String response = userGatewayImpl.deleteUser(inputId);


        assertThat(response).isEqualTo(success);
    }

    @Test
    void updateUser() throws Exception {
        HashMap<String, Object> inputJson = new HashMap<String, Object>();
        inputJson.put("email", "test@gmail.com");
        inputJson.put("firstName", "firstName");
        inputJson.put("middleName", "lastName");
        inputJson.put("lastName", "lastName");
        inputJson.put("birthDate", "1998-09-01");
        inputJson.put("password", "password");

        UserDto userDto = UserDto
                .builder()
                .email("test@gmail.com")
                .firstName("first")
                .middleName("middle")
                .lastName("last")
                .birthDate(
                        LocalDate.of(1995, 01, 8)
                )
                .password("password")
                .build();



        Mockito.when(userGatewayImpl.createUser(any(UserDto.class))).thenReturn(userDto);
        userDto.setEmail("updatedemail@gmail.com");
        Mockito.when(userGatewayImpl.updateUser(inputId, userDto)).thenReturn(userDto);

        mockMvc.perform(MockMvcRequestBuilders.put(baseUrl +"/update/{id}", inputId).contentType(MediaType.APPLICATION_JSON)
                        .content(CustomFormatter.convertObjectToJson(inputJson)).characterEncoding("utf-8"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());


        UserDto userRes = userGatewayImpl.updateUser(inputId, userDto);

        // then
        assertThat(userRes.getEmail()).isEqualTo(userDto.getEmail());
    }

    @Test
    void verifyUser() throws Exception {
        // given
        UserDto userDto = UserDto
                .builder()
                .email("test@gmail.com")
                .firstName("first")
                .middleName("middle")
                .lastName("last")
                .birthDate(
                        LocalDate.of(1995, 01, 8)
                )
                .password(bCryptPasswordEncoder.encode("password"))
                .build();

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());

        // when
        Mockito.when(userGatewayImpl.verifyUser(auth)).thenReturn(userDto);

        mockMvc
                .perform(MockMvcRequestBuilders.get(baseUrl + "/verify").contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        UserDto userRes = userGatewayImpl.verifyUser(auth);

        // then
        assertThat(userRes.getEmail()).isEqualTo(auth.getName());

    }


}