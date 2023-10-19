package com.fintechbackend.domain.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintechbackend.domain.repositories.UserRepository;
import com.fintechbackend.domain.user.User;
import com.fintechbackend.domain.user.UserType;
import com.fintechbackend.domain.user.dtos.UserDTO;

@Service
public class UserService {

  @Autowired
  private UserRepository repository;

  public void validateTransaction(User sender, BigDecimal amount) throws Exception {
    if (sender.getUserType() == UserType.MERCHANT) {
      throw new Exception("Usuário do tipo Lojista não autorizado a realizar essa transação");
    }

    if (sender.getBalance().compareTo(amount) < 0) {
      throw new Exception("Saldo insuficiente");
    }
  }

  public User findUserById(UUID id) throws Exception {
    return this.repository.findUserById(id).orElseThrow(() -> new Exception("Usuário não encontrado"));
  }

  public User createUser(UserDTO data) {
    User newUser = new User(data);
    this.saveUser(newUser);
    return newUser;
  }

  public List<User> getAllUsers() {
    return this.repository.findAll();
  }

  public void saveUser(User user) {
    this.repository.save(user);
  }
}
