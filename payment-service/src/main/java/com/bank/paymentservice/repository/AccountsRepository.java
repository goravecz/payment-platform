package com.bank.paymentservice.repository;

import com.bank.paymentservice.model.Account;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends JpaRepository<Account, UUID> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<Account> findById(UUID id);
}
