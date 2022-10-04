package com.calibrar.identityservice.gateway.database;

import com.calibrar.identityservice.domain.dto.UserDto;
import com.calibrar.identityservice.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserGatewayImpl implements UserGateway {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
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

        final UserDto responseDto =  UserDto
                .builder()
                .email(response.getEmail())
                .firstName(response.getFirstName())
                .middleName(response.getMiddleName())
                .lastName(response.getLastName())
                .birthDate(response.getBirthDate())
                .password(response.getPassword())
                .build();

        return responseDto;
    }
}
