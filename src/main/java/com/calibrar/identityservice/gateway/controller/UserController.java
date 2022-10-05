package com.calibrar.identityservice.gateway.controller;

import com.calibrar.identityservice.domain.dto.UserDto;
import com.calibrar.identityservice.use_cases.GetUserByEmailUseCase;
import com.calibrar.identityservice.use_cases.RegisterUserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
    private final RegisterUserUseCase registerUserUseCase;
    private final GetUserByEmailUseCase getUserByEmailUseCase;


    @PostMapping("/register")
    public final ResponseEntity<UserDto> createuser(@Valid @RequestBody UserDto userDto) {
        final UserDto responseDto = registerUserUseCase.createUser(userDto);

        return new ResponseEntity(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    public final ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        final UserDto responseDto = getUserByEmailUseCase.getUserByEmail(email);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
