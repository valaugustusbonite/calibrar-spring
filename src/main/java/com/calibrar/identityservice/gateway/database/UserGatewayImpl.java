package com.calibrar.identityservice.gateway.database;

import com.calibrar.identityservice.common.enums.UserStatus;
import com.calibrar.identityservice.common.exception.EntityNotFoundException;
import com.calibrar.identityservice.domain.dto.UserDto;
import com.calibrar.identityservice.domain.entity.User;
import com.calibrar.identityservice.gateway.exception.EmailAlreadyExistsException;
import com.calibrar.identityservice.gateway.exception.UserAlreadyDeletedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

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
            .status(UserStatus.ACTIVE)
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

    @Override
    public UserDto getUser(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw  new EntityNotFoundException("User does not exist");
        }

        final UserDto responseDto = new UserDto().convertToDto(user.get());

        return responseDto;
    }

    @Override
    public String deleteUser(Long id) {
        final Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new EntityNotFoundException("User does not exist");
        }

        if (user.isPresent() && user.get().getStatus() == UserStatus.DELETED) {
            throw new UserAlreadyDeletedException("Cannot delete a user that has already been deleted");
        }

        final String name = user.get().getFirstName() + " " + user.get().getLastName();

        user.get().setStatus(UserStatus.DELETED);

        userRepository.save(user.get());
        return name + " has been successfully deleted";
    }

    @Override
    public UserDto updateUser(Long id, UserDto userInput) {
        final Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new EntityNotFoundException("User does not exist");
        }

        if (user.isPresent() && user.get().getStatus() == UserStatus.DELETED) {
            throw new UserAlreadyDeletedException("Cannot update a deleted user");
        }

        final User updatedUser = user.get();

        if (userInput.getEmail() != null) {
            updatedUser.setEmail(userInput.getEmail());
        }
        if (userInput.getPassword() != null) {
            updatedUser.setPassword(bCryptPasswordEncoder.encode(userInput.getPassword()));
        }
        if (userInput.getFirstName() != null) {
            updatedUser.setFirstName(userInput.getFirstName());
        }
        if (userInput.getLastName() != null) {
            updatedUser.setLastName(userInput.getLastName());
        }
        if (userInput.getMiddleName() != null) {
            updatedUser.setMiddleName(userInput.getMiddleName());
        }
        if (userInput.getBirthDate() != null) {
            updatedUser.setBirthDate(userInput.getBirthDate());
        }



        final User response = userRepository.save(updatedUser);

        final UserDto responseDto = new UserDto().convertToDto(response);

        return responseDto;
    }

}
