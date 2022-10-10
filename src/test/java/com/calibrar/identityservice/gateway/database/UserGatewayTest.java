package com.calibrar.identityservice.gateway.database;

import com.calibrar.identityservice.common.enums.UserStatus;
import com.calibrar.identityservice.common.exception.EntityNotFoundException;
import com.calibrar.identityservice.domain.dto.UserDto;
import com.calibrar.identityservice.domain.entity.User;
import com.calibrar.identityservice.gateway.exception.EmailAlreadyExistsException;
import com.calibrar.identityservice.gateway.exception.UserAlreadyDeletedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserGatewayImplTest {

    @Mock
    private UserRepository mockRepository;
    private UserGatewayImpl userGateway;

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private static Long inputId = Long.valueOf(1);

    private UserDto userDto;

    private User userEntity;

    @BeforeEach
    void setUp() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        userGateway = new UserGatewayImpl(mockRepository, bCryptPasswordEncoder);

        userDto = UserDto
                .builder()
                .email("test@gmail.com")
                .firstName("first")
                .middleName("middle")
                .lastName("last")
                .birthDate(LocalDate.of(1998, 01, 20))
                .password("password")
                .build();

        userEntity = User
                .builder()
                .id(inputId)
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .middleName(userDto.getMiddleName())
                .lastName(userDto.getLastName())
                .birthDate(userDto.getBirthDate())
                .status(UserStatus.ACTIVE)
                .build();
    }


    @Test
    void shouldCreateUserAndReturnUserInstance() {

        //given
        Mockito.when(mockRepository.save(any(User.class))).thenReturn(userEntity);

        // Act
        final UserDto actual = userGateway.createUser(userDto);

        // Assert
        assertThat(actual).usingRecursiveComparison().isEqualTo(userEntity);
        verify(mockRepository, times(1)).save(any(User.class));

    }

    @Test
    void willThrowIfUserFoundByEmailAlreadyExists() {
        //given

        given(mockRepository.findByEmail(userDto.getEmail())).willReturn(Optional.of(userEntity));
        //when

        //then
        assertThatThrownBy(() -> userGateway.createUser(userDto))
                .isInstanceOf(EmailAlreadyExistsException.class)
                .hasMessageContaining("Email already exists.");

        verify(mockRepository, never()).save(any());
    }

    @Test
    void shouldGetUserUsingUniqueId() {
        Mockito.when(mockRepository.findById(inputId)).thenReturn(Optional.of(userEntity));

        final UserDto actual = userGateway.getUser(inputId);

        assertThat(actual).usingRecursiveComparison().isEqualTo(userEntity);
        verify(mockRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void shouldGetUserUsingUniqueEmail() {
        Mockito.when(mockRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(userEntity));

        final UserDto actual = userGateway.getUserByEmail(userDto.getEmail());

        assertThat(actual).usingRecursiveComparison().isEqualTo(userEntity);
        verify(mockRepository, times(1)).findByEmail(any(String.class));
    }

    @Test
    void shouldSoftDeleteUserUsingUniqueIdIfUserExists() {
        Mockito.when(mockRepository.findById(inputId)).thenReturn(Optional.of(userEntity));

        final String actual = userGateway.deleteUser(inputId);
        userEntity.setStatus(UserStatus.DELETED);
        final String name = userEntity.getFirstName() + " " + userEntity.getLastName();
        final String expected = name + " has been successfully deleted";

        assertThat(actual).isEqualTo(expected);
        assertThat(userEntity.getStatus()).isEqualTo(UserStatus.DELETED);
        verify(mockRepository, times(1)).save(any(User.class));
    }

    @Test
    void willThrowIfUserBeingDeletedDoesNotExist() {

        Mockito.when(mockRepository.findById(inputId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userGateway.deleteUser(inputId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("User does not exist");

        verify(mockRepository, never()).save(any());
    }

    @Test
    void willThrowIfUserBeingDeletedIsAlreadyDeleted() {
        userEntity.setStatus(UserStatus.DELETED);
        Mockito.when(mockRepository.findById(inputId)).thenReturn(Optional.of(userEntity));

        assertThatThrownBy(() -> userGateway.deleteUser(inputId))
                .isInstanceOf(UserAlreadyDeletedException.class)
                .hasMessageContaining("Cannot delete a user that has already been deleted");

        verify(mockRepository, never()).save(any());
    }

    @Test
    void shouldUpdateUserUsingUniqueIdIfUserExists() {
        Mockito.when(mockRepository.findById(inputId)).thenReturn(Optional.of(userEntity));
        userEntity.setFirstName("updated");
        Mockito.when(mockRepository.save(userEntity)).thenReturn(userEntity);

        userDto.setFirstName("updated");
        final UserDto actual = userGateway.updateUser(inputId, userDto);


        assertThat(actual.getFirstName()).isEqualTo(userDto.getFirstName());
        verify(mockRepository, times(1)).save(any(User.class));
    }


}