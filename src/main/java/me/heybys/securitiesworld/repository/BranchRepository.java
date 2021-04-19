package me.heybys.securitiesworld.repository;

import me.heybys.securitiesworld.entity.Branch;
import me.heybys.securitiesworld.vo.BranchStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, String> {

    @Query(value =
            "SELECT br_code AS brCode, br_name AS brName, SUM(sum_amt) AS sumAmt " +
                    "FROM ( " +
                    "    SELECT IFNULL(acc_stt.sum_amt, 0) AS sum_amt, b.br_code, b.br_name " +
                    "    FROM ( " +
                    "        SELECT t.acc_no AS acc_no, SUM(t.amount - t.fee) AS sum_amt " +
                    "        FROM transaction_log t" +
                    "        WHERE t.cancel_yn = 'N' AND t.date LIKE :year% " +
                    "        GROUP BY acc_no) acc_stt " +
                    "    JOIN account a ON a.acc_no = acc_stt.acc_no " +
                    "    RIGHT OUTER JOIN branch b ON a.br_code = b.br_code) " +
                    "GROUP BY brCode, brName " +
                    "ORDER BY sumAmt DESC ", nativeQuery = true)
    List<BranchStatistics> findBranchStatisticsByYear(@Param("year") String year);

    @Query(value =
            "SELECT br_code AS brCode, br_name AS brName, SUM(sum_amt) AS sumAmt " +
                    "FROM ( " +
                    "    SELECT IFNULL(acc_stt.sum_amt, 0) AS sum_amt, b.br_code, b.br_name " +
                    "    FROM ( " +
                    "        SELECT t.acc_no AS acc_no, SUM(t.amount - t.fee) AS sum_amt " +
                    "        FROM transaction_log t" +
                    "        WHERE t.cancel_yn = 'N' " +
                    "        GROUP BY acc_no) acc_stt " +
                    "    JOIN account a ON a.acc_no = acc_stt.acc_no " +
                    "    RIGHT OUTER JOIN branch b ON a.br_code = b.br_code" +
                    "    WHERE b.br_name = :brName) " +
                    "GROUP BY brCode, brName " +
                    "ORDER BY sumAmt DESC ", nativeQuery = true)
    List<BranchStatistics> findBranchStatisticsByBrName(@Param("brName") String brName);

    Optional<Branch> findByBrName(String brName);

    void deleteByBrName(String brName);
}
