package me.heybys.securitiesworld.repository;

import me.heybys.securitiesworld.entity.Branch;
import me.heybys.securitiesworld.entity.TransactionLog;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TransactionLogRepositoryTest {

    @Autowired
    private TransactionLogRepository transactionLogRepository;

    @Test
    @DisplayName("거래내역 생성하기")
    public void save() {
        //given
        TransactionLog log = new TransactionLog("20210101", "111333000001", 1L, 100000L, 100L, "N");

        //when
        transactionLogRepository.save(log);
        Optional<TransactionLog> result = transactionLogRepository.findById(log.getId());

        //then
        assertThat(result).isNotEmpty();
        assertThat(result.get().getId()).isEqualTo(log.getId());
    }

    @Test
    @DisplayName("ID로 거래내역 조회하기")
    public void findById() {
        //given
        TransactionLog log1 = new TransactionLog("20210101", "111333000001", 1L, 100000L, 100L, "N");
        TransactionLog log2 = new TransactionLog("20210102", "111333000002", 1L, 100000L, 100L, "N");

        //when
        transactionLogRepository.save(log1);
        transactionLogRepository.save(log2);
        Optional<TransactionLog> result = transactionLogRepository.findById(log1.getId());

        //then
        assertThat(result).isNotEmpty();
        assertThat(result.get().getId()).isEqualTo(log1.getId());
    }

    @Test
    @DisplayName("전체 거래내역 조회하기")
    public void findAll() {
        //given
        TransactionLog log1 = new TransactionLog("20210101", "111333000001", 1L, 100000L, 100L, "N");
        TransactionLog log2 = new TransactionLog("20210102", "111333000002", 1L, 100000L, 100L, "N");

        //when
        transactionLogRepository.save(log1);
        transactionLogRepository.save(log2);
        List<TransactionLog> result = transactionLogRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.stream().filter(log -> log.getId().equals(log1.getId()))).isNotEmpty();
        assertThat(result.stream().filter(log -> log.getId().equals(log2.getId()))).isNotEmpty();
    }

    @Test
    @DisplayName("거래내역 수정하기")
    public void update() {
        //given
        TransactionLog log = new TransactionLog("20210101", "111333000001", 1L, 100000L, 100L, "N");

        //when
        transactionLogRepository.save(log);
        log.setCancelYn("Y");
        transactionLogRepository.save(log);
        Optional<TransactionLog> result = transactionLogRepository.findById(log.getId());

        //then
        assertThat(result).isNotEmpty();
        assertThat(result.get().getId()).isEqualTo(log.getId());
        assertThat(result.get().getCancelYn()).isEqualTo(log.getCancelYn());
    }

    @Test
    @DisplayName("거래내역 삭제하기")
    public void delete() {
        //given
        TransactionLog log = new TransactionLog("20210101", "111333000001", 1L, 100000L, 100L, "N");

        //when
        transactionLogRepository.save(log);
        Optional<TransactionLog> result = transactionLogRepository.findById(log.getId());
        assertThat(result).isNotEmpty();
        transactionLogRepository.deleteById(log.getId());
        result = transactionLogRepository.findById(log.getId());

        //then
        assertThat(result).isEmpty();
    }

}
