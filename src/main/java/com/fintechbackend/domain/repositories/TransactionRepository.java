package com.fintechbackend.domain.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fintechbackend.domain.transactions.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

}
