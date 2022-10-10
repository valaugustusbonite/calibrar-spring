package com.calibrar.identityservice.gateway.database;

import com.calibrar.identityservice.common.enums.UserStatus;
import com.calibrar.identityservice.domain.dto.UserDto;
import com.calibrar.identityservice.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void itShouldFindUserByEmail() {
        //given
        String email = "test@gmail.com";
        User user = User.builder()
                .email(email)
                .firstName("John")
                .lastName("McArthur")
                .birthDate(LocalDate.of(1995, 01, 25))
                .password("test-password")
                .status(UserStatus.ACTIVE)
                .build();

        userRepository.save(user);
        //when

        User retrievedUser = userRepository.findByEmail(email).get();
        //then
        assertThat(retrievedUser.getFirstName()).isEqualTo(user.getFirstName());
    }

    @Test
    void itShouldCheckIfUserDoesNotExist() {
        //given
        String email = "test@gmail.com";
        //when

        Optional<User> retrievedUser = userRepository.findByEmail(email);
        //then
        assertThat(retrievedUser).isEqualTo(Optional.empty());
    }
}