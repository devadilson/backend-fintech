package com.fintechbackend.domain.repositories;

import com.fintechbackend.domain.user.User;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findUserByDocument(String document);

  Optional<User> findUserById(UUID id);
}
