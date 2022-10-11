package com.calibrar.identityservice.gateway.controller;

import com.calibrar.identityservice.common.enums.UserStatus;
import com.calibrar.identityservice.domain.dto.UserDto;
import com.calibrar.identityservice.domain.entity.User;
import com.calibrar.identityservice.gateway.database.UserGatewayImpl;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.ResultActions.*;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
        bCryptPasswordEncoder = new BCryptPasswordEncoder();

    }


    @Test
    void createUser() throws Exception {

        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();

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

        String requestJson=ow.writeValueAsString(userDto);

        Mockito.when(userGatewayImpl.createUser(any(UserDto.class))).thenReturn(userDto);



//        mockMvc
//                .perform(MockMvcRequestBuilders.post(baseUrl + "/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestJson))
//                .andExpect(status().isCreated());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/user/register")
                                .content("{" +
                                    "'email': 'anne@gmail.com, " +
                                    "'firstName': 'Anne', " +
                                    "'lastName': 'Concepcion', " +
                                    "'birthDate': '1998-09-01', " +
                                    "'password': 'password123'" +
                                "}")
                                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());


        UserDto userRes = userGatewayImpl.createUser(userDto);

        // then
        assertThat(userRes.getFirstName()).isEqualTo(userDto.getFirstName());
    }

    @Test
    void getUserByEmail() {
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

        this.mockMvc
                .perform(MockMvcRequestBuilders.get(baseUrl + "/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        UserDto userRes = userGatewayImpl.getUser(inputId);

        // then
        assertThat(userRes.getFirstName()).isEqualTo(userDto.getFirstName());

    }

    @Test
    void deleteUser() {
    }

    @Test
    void updateUser() {
    }
}