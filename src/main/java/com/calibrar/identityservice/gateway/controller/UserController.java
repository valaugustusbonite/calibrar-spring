package com.calibrar.identityservice.gateway.controller;

import com.calibrar.identityservice.domain.dto.UserDto;
import com.calibrar.identityservice.use_cases.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final RegisterUserUseCase registerUserUseCase;
    private final GetUserByEmailUseCase getUserByEmailUseCase;
    private final GetUserUseCase getUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final VerifyUserUseCase verifyUserUseCase;


    @PostMapping("/register")
    public final ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        final UserDto responseDto = registerUserUseCase.createUser(userDto);

        return new ResponseEntity(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/email/{email}")
    public final ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        final UserDto responseDto = getUserByEmailUseCase.getUserByEmail(email);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public final ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        final UserDto responseDto = getUserUseCase.getUser(id);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/delete/{id}")
    public final ResponseEntity<String> deleteUser(@PathVariable Long id) {
        final String successMessage = deleteUserUseCase.deleteUser(id);

        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public final ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userInput) {
        final UserDto responseDto = updateUserUseCase.updateUser(id, userInput);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/verify")
    public final ResponseEntity<UserDto> verifyUser(Authentication auth) {
        final UserDto responseDto = verifyUserUseCase.verifyUser(auth);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
