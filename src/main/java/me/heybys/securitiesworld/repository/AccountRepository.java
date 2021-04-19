package me.heybys.securitiesworld.repository;

import me.heybys.securitiesworld.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, String> {

    @Modifying
    @Query(value = "UPDATE account SET br_code = :to WHERE br_code = :from", nativeQuery = true)
    void updateBrCode(@Param("from") String from, @Param("to") String to);
}
