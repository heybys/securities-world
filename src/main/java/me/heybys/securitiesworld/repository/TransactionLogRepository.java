package me.heybys.securitiesworld.repository;

import me.heybys.securitiesworld.entity.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {

    @Query(value = "SELECT DISTINCT SUBSTRING(date, 1, 4) AS year FROM transaction_log WHERE cancel_yn = 'N'", nativeQuery = true)
    List<String> findTransactionLogYears();
}
