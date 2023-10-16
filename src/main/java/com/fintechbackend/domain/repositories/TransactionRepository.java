package com.fintechbackend.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fintechbackend.domain.transactions.Transactions;

public interface TransactionRepository extends JpaRepository<Transactions, Long> {

}
