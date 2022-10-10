package com.calibrar.identityservice.gateway.controller;

import com.calibrar.identityservice.common.enums.UserStatus;
import com.calibrar.identityservice.domain.dto.UserDto;
import com.calibrar.identityservice.domain.entity.User;
import com.calibrar.identityservice.gateway.database.UserGatewayImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    private final static String baseUrl = "/api/v1/user";
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private UserGatewayImpl userGatewayImpl;

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public void setup() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        bCryptPasswordEncoder = new BCryptPasswordEncoder();

    }


    @Test
    void createUser() {
    }

    @Test
    void getUserByEmail() {
    }

//    @Test
//    void getUser() {
//        //given
//        UserDto userDto = UserDto
//                .builder()
//                .email("test@gmail.com")
//                .firstName("first")
//                .middleName("middle")
//                .lastName("last")
//                .birthDate(
//                        LocalDate.of(1995, 01, 8)
//                )
//                .password(bCryptPasswordEncoder.encode("password"))
//                .build();
//
//        User userEntity = User
//                .builder()
//                .id(Long.valueOf(1))
//                .email(userDto.getEmail())
//                .firstName(userDto.getFirstName())
//                .middleName(userDto.getMiddleName())
//                .lastName((userDto.getLastName()))
//                .birthDate(userDto.getBirthDate())
//                .password(userDto.getPassword())
//                .status(UserStatus.ACTIVE)
//                .build();
//
//        Optional<User> optionalUser = Optional.of(userEntity);
//
//        given(userGatewayImpl.getUser(userEntity.getId())).willReturn(userDto);
//
//        this.mockMvc
//                        .perform(MockMvcRequestBuilders.get(baseUrl + "/{id}", 1);
//
//    }

    @Test
    void deleteUser() {
    }

    @Test
    void updateUser() {
    }
}