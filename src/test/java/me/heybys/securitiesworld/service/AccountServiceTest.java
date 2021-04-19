package me.heybys.securitiesworld.service;


import me.heybys.securitiesworld.entity.Account;
import me.heybys.securitiesworld.entity.Branch;
import me.heybys.securitiesworld.entity.TransactionLog;
import me.heybys.securitiesworld.repository.AccountRepository;
import me.heybys.securitiesworld.repository.BranchRepository;
import me.heybys.securitiesworld.repository.TransactionLogRepository;
import me.heybys.securitiesworld.vo.AccountStatisticsInfo;
import me.heybys.securitiesworld.vo.DormantAccount;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class AccountServiceTest {

    @Autowired
    private BranchRepository branchRepository;

    private AccountRepository accountRepository;

    private TransactionLogRepository transactionLogRepository;

    private AccountService accountService;

    @Autowired
    public AccountServiceTest(AccountRepository accountRepository, TransactionLogRepository transactionLogRepository) {
        this.accountRepository = accountRepository;
        this.transactionLogRepository = transactionLogRepository;
        this.accountService = new AccountService(this.accountRepository, this.transactionLogRepository);
    }

    @Test
    @DisplayName("모든 계좌정보 조회하기")
    public void findAccounts() {
        //given
        Account account1 = new Account("111222000001", "TestAccount1", "A");
        accountRepository.save(account1);
        Account account2 = new Account("111222000002", "TestAccount2", "A");
        accountRepository.save(account2);

        //when
        List<Account> result = accountService.findAccounts();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.stream().filter(acc -> acc.getAccNo().equals(account1.getAccNo()))).isNotEmpty();
        assertThat(result.stream().filter(acc -> acc.getAccNo().equals(account2.getAccNo()))).isNotEmpty();
    }

    @Test
    @DisplayName("계좌번호로 계좌정보 조회하기")
    public void findAccount() {
        //given
        Account account1 = new Account("111222000001", "TestAccount1", "A");
        accountRepository.save(account1);
        Account account2 = new Account("111222000002", "TestAccount2", "A");
        accountRepository.save(account2);

        //when
        Optional<Account> result = accountService.findAccount(account1.getAccNo());

        //then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getAccNo()).isEqualTo(account1.getAccNo());
    }

    @Test
    @DisplayName("연도별 계좌통계정보 조회하기")
    public void getAccountStatisticsInfoByYear() {
        //given
        Integer year = 2021;

        Account account1 = new Account("111222000001", "TestAccount1", "A");
        accountRepository.save(account1);
        TransactionLog log1 = new TransactionLog(year + "0101", account1.getAccNo(), 1L, 1000001L, 101L, "N");
        transactionLogRepository.save(log1);

        Account account2 = new Account("111222000002", "TestAccount2", "A");
        accountRepository.save(account2);
        TransactionLog log2 = new TransactionLog(year + "0101", account2.getAccNo(), 1L, 1000002L, 102L, "N");
        transactionLogRepository.save(log2);
        TransactionLog log3 = new TransactionLog(year + "0101", account2.getAccNo(), 1L, 1000003L, 103L, "N");
        transactionLogRepository.save(log3);

        //when
        AccountStatisticsInfo result = accountService.getAccountStatisticsInfoByYear(year);

        //then
        assertThat(result.getYear()).isEqualTo(year);
        assertThat(result.getAccNo()).isEqualTo(account2.getAccNo());
        assertThat(result.getSumAmt()).isEqualTo(log2.getAmount() + log3.getAmount() - log2.getFee() - log3.getFee());
    }

    @Test
    @DisplayName("연도별 휴면계좌 조회하기")
    public void getDormantAccountsByYear() {
        //given
        Integer year = 2021;

        Account account1 = new Account("111222000001", "TestAccount1", "A");
        accountRepository.save(account1);
        TransactionLog log1 = new TransactionLog(year + "0101", account1.getAccNo(), 1L, 1000001L, 101L, "N");
        transactionLogRepository.save(log1);

        Account account2 = new Account("111222000002", "TestAccount2", "A");
        accountRepository.save(account2);

        Account account3 = new Account("111222000003", "TestAccount3", "A");
        accountRepository.save(account3);

        //when
        List<DormantAccount> result = accountService.getDormantAccountsByYear(year);

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.stream().filter(acc -> acc.getAccNo().equals(account2.getAccNo()))).isNotEmpty();
        assertThat(result.stream().filter(acc -> acc.getAccNo().equals(account3.getAccNo()))).isNotEmpty();
    }

}