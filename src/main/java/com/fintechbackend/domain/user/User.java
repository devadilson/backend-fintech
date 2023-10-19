package com.fintechbackend.domain.user;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import com.fintechbackend.domain.user.dtos.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {
  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;

  private String firstName;
  private String lastName;

  @Column(unique = true)
  private String document;

  @Column(unique = true)
  private String email;

  private String password;
  private BigDecimal balance;

  @Enumerated(EnumType.STRING)
  private UserType userType;

  @CreationTimestamp
  private LocalDateTime createdAt;

  public User(UserDTO data) {
    this.firstName = data.firstName();
    this.lastName = data.lastName();
    this.balance = data.balance();
    this.userType = data.userType();
    this.password = data.password();
    this.document = data.document();
    this.email = data.email();
  }
}