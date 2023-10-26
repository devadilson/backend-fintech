package com.fintechbackend.repositories;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import com.fintechbackend.domain.repositories.UserRepository;
import com.fintechbackend.domain.user.User;
import com.fintechbackend.domain.user.UserType;
import com.fintechbackend.domain.user.dtos.UserDTO;

import jakarta.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @Autowired
  EntityManager entityManager;

  @Test
  @DisplayName("Should get User successfully from DB")
  void findUserByDocumentCase1() {
    String document = "99999999901";
    UserDTO data = new UserDTO("Fernanda", "Teste", document, new BigDecimal(10), "test@gmail.com", "44444",
        UserType.COMMON);
    this.createUser(data);

    Optional<User> result = this.userRepository.findUserByDocument(document);

    assertThat(result.isPresent()).isTrue();
  }

  @Test
  @DisplayName("Should not get User from DB when user not exists")
  void findUserByDocumentCase2() {
    String document = "99999999901";

    Optional<User> result = this.userRepository.findUserByDocument(document);

    assertThat(result.isEmpty()).isTrue();
  }

  private User createUser(UserDTO data) {
    User newUser = new User(data);
    this.entityManager.persist(newUser);
    return newUser;
  }

}
