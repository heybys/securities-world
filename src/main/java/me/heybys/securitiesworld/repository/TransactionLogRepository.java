package me.heybys.securitiesworld.repository;

import me.heybys.securitiesworld.entity.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long>{

    Optional<TransactionLog> findByAccNo(String accNo);
}
