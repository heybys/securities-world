package me.heybys.securitiesworld.repository;

import me.heybys.securitiesworld.entity.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {
}
