package me.heybys.securitiesworld.service;

import me.heybys.securitiesworld.entity.Account;
import me.heybys.securitiesworld.entity.Branch;
import me.heybys.securitiesworld.entity.TransactionLog;
import me.heybys.securitiesworld.exception.BranchStatisticsNotFoundException;
import me.heybys.securitiesworld.repository.AccountRepository;
import me.heybys.securitiesworld.repository.BranchRepository;
import me.heybys.securitiesworld.repository.TransactionLogRepository;
import me.heybys.securitiesworld.vo.BranchIntegration;
import me.heybys.securitiesworld.vo.BranchStatistics;
import me.heybys.securitiesworld.vo.BranchStatisticsInfos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class BranchServiceTest {

    private BranchRepository branchRepository;

    private AccountRepository accountRepository;

    private TransactionLogRepository transactionLogRepository;

    private BranchService branchService;

    @Autowired
    public BranchServiceTest(BranchRepository branchRepository, AccountRepository accountRepository, TransactionLogRepository transactionLogRepository) {
        this.branchRepository = branchRepository;
        this.accountRepository = accountRepository;
        this.transactionLogRepository = transactionLogRepository;
        this.branchService = new BranchService(this.branchRepository, this.accountRepository, this.transactionLogRepository);
    }

    @Test
    @DisplayName("모든 연도별 관리점통계정보 조회하기")
    public void getBranchStatisticsInfos() {
        //given
        Integer year = 2021;

        Branch branch = new Branch("A", "TestBranch");
        branchRepository.save(branch);
        Account account = new Account("111222000001", "TestAccount", branch.getBrCode());
        accountRepository.save(account);
        TransactionLog log1 = new TransactionLog(year + "0101", account.getAccNo(), 1L, 1000001L, 101L, "N");
        transactionLogRepository.save(log1);
        TransactionLog log2 = new TransactionLog(year + "0101", account.getAccNo(), 1L, 1000002L, 102L, "N");
        transactionLogRepository.save(log2);

        //when
        List<BranchStatisticsInfos> list = branchService.getBranchStatisticsInfos();

        //then
        assertThat(list.size()).isEqualTo(1);

        BranchStatisticsInfos info = list.get(0);
        List<BranchStatistics> dataList = info.getDataList();

        assertThat(info.getYear()).isEqualTo(year); //check year
        assertThat(dataList.size()).isEqualTo(1);

        BranchStatistics statistics = dataList.get(0);

        assertThat(statistics.getBrCode()).isEqualTo(branch.getBrCode()); //check branch
        assertThat(statistics.getSumAmt()).isEqualTo(log1.getAmount() + log2.getAmount() - log1.getFee() - log2.getFee()); //check sumAmt
    }

    @Test
    @DisplayName("관지점명별 관리점통계정보 조회하기_성공")
    public void getBranchStatistics_success() {
        //given
        Branch branch = new Branch("A", "TestBranch");
        branchRepository.save(branch);
        Account account = new Account("111222000001", "TestAccount", branch.getBrCode());
        accountRepository.save(account);
        TransactionLog log1 = new TransactionLog("20210101", account.getAccNo(), 1L, 1000001L, 101L, "N");
        transactionLogRepository.save(log1);
        TransactionLog log2 = new TransactionLog("20210101", account.getAccNo(), 1L, 1000002L, 102L, "N");
        transactionLogRepository.save(log2);

        //when
        BranchStatistics statistics = branchService.getBranchStatistics(branch.getBrName());

        //then
        assertThat(statistics.getBrCode()).isEqualTo(branch.getBrCode()); //check branch
        assertThat(statistics.getSumAmt()).isEqualTo(log1.getAmount() + log2.getAmount() - log1.getFee() - log2.getFee()); //check sumAmt
    }

    @Test
    @DisplayName("관지점명별 관리점통계정보 조회하기_실패")
    public void getBranchStatistics_fail() {
        //given
        Branch branch = new Branch("A", "TestBranch");
        branchRepository.save(branch);
        Account account = new Account("111222000001", "TestAccount", branch.getBrCode());
        accountRepository.save(account);
        TransactionLog log1 = new TransactionLog("20210101", account.getAccNo(), 1L, 1000001L, 101L, "N");
        transactionLogRepository.save(log1);
        TransactionLog log2 = new TransactionLog("20210101", account.getAccNo(), 1L, 1000002L, 102L, "N");
        transactionLogRepository.save(log2);

        //when
        Branch closedBranch = new Branch("B", "ClosedBranch");
        BranchStatisticsNotFoundException e = assertThrows(BranchStatisticsNotFoundException.class, () -> branchService.getBranchStatistics(closedBranch.getBrName()));

        //then
        assertThat(e.getBrName()).isEqualTo(closedBranch.getBrName());
    }

    @Test
    @DisplayName("관리점 통합하기")
    public void mergeBranch() {
        //given
        Branch branch1 = new Branch("A", "TestBranch1");
        branchRepository.save(branch1);
        Account account1 = new Account("111222000001", "TestAccount1", branch1.getBrCode());
        accountRepository.save(account1);

        Branch branch2 = new Branch("B", "TestBranch2");
        branchRepository.save(branch2);
        Account account2 = new Account("111222000002", "TestAccount2", "B");
        accountRepository.save(account2);

        //when
        BranchIntegration branchIntegration = new BranchIntegration(branch2.getBrName(), branch1.getBrName());
        branchService.mergeBranch(branchIntegration);

        //then
        assertThat(branchRepository.existsById(branch2.getBrCode())).isFalse();
    }
}