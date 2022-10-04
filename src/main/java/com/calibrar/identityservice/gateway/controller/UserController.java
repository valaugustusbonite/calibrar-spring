package com.calibrar.identityservice.gateway.controller;

import com.calibrar.identityservice.domain.dto.UserDto;
import com.calibrar.identityservice.use_cases.RegisterUserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final RegisterUserUseCase registerUserUseCase;

    public UserController(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping("/register")
    public final ResponseEntity<UserDto> createuser(@Valid @RequestBody UserDto userDto) {
        final UserDto responseDto = registerUserUseCase.createUser(userDto);

        return new ResponseEntity(responseDto, HttpStatus.CREATED);
    }
}
