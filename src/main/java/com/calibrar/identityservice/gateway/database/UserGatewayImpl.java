package com.calibrar.identityservice.gateway.database;

import com.calibrar.identityservice.common.exception.EntityNotFoundException;
import com.calibrar.identityservice.domain.dto.UserDto;
import com.calibrar.identityservice.domain.entity.User;
import com.calibrar.identityservice.gateway.exception.EmailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class UserGatewayImpl implements UserGateway {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        Optional<User> existingUser = userRepository.findByEmail(userDto.getEmail());

        if (existingUser.isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists.");
        }

        final String hashedPassword = bCryptPasswordEncoder.encode(userDto.getPassword());

        userDto.setPassword(hashedPassword);

        final User newUserEntity = User
            .builder()
            .email(userDto.getEmail())
            .firstName(userDto.getFirstName())
            .middleName(userDto.getMiddleName())
            .lastName(userDto.getLastName())
            .birthDate(userDto.getBirthDate())
            .password(userDto.getPassword())
            .build();

        final User response = userRepository.save(newUserEntity);

        final UserDto responseDto = new UserDto().convertToDto(response);

        return responseDto;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        Optional<User> user  = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new EntityNotFoundException("User with the email" + email + "does not exist.");
        }

        final UserDto responseDto = new UserDto().convertToDto(user.get());
        return responseDto;
    }
}
