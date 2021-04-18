package me.heybys.securitiesworld.service;

import me.heybys.securitiesworld.entity.Account;
import me.heybys.securitiesworld.entity.TransactionLog;
import me.heybys.securitiesworld.repository.AccountRepository;
import me.heybys.securitiesworld.repository.TransactionLogRepository;
import me.heybys.securitiesworld.vo.AccountStatisticsInfo;
import me.heybys.securitiesworld.vo.DormantAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final TransactionLogRepository transactionLogRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, TransactionLogRepository transactionLogRepository) {
        this.accountRepository = accountRepository;
        this.transactionLogRepository = transactionLogRepository;
    }

    /**
     * 계좌정보 조회
     *
     * @return 계좌정보 목록
     */
    public List<Account> findAccounts() {
        return accountRepository.findAll();
    }

    /**
     * 계좌번호로 계좌정보 조회
     *
     * @param accNo 계좌번호
     * @return 계좌정보
     */
    public Optional<Account> findAccount(String accNo) {
        return accountRepository.findById(accNo);
    }

    /**
     * 연도별 계좌통계정보 조회
     *
     * @return 계좌통계정보
     */
    public AccountStatisticsInfo getAccountStatisticsInfoByYear(Integer year) {

        // 연도에 해당하는 거래 목록
        List<TransactionLog> filtered = transactionLogRepository.findAll().stream().filter(log -> log.getDate().startsWith(Integer.toString(year))).collect(Collectors.toList());

        Map<String, Long> tempMap = new HashMap<>();

        // 계좌별 취소되지 않은 거래량 취합
        filtered.forEach(log -> {
            if ("N".equals(log.getCancelYn())) {
                Long amt = log.getAmount() - log.getFee();
                if (tempMap.get(log.getAccNo()) == null) {
                    tempMap.put(log.getAccNo(), amt);
                } else {
                    Long sumAmt = tempMap.get(log.getAccNo());
                    tempMap.put(log.getAccNo(), sumAmt + amt);
                }
            }
        });

        // 최대 거래금액 계좌 추출
        String maxAccNo = null;
        Long maxSumAmt = null;
        for (Map.Entry<String, Long> entry : tempMap.entrySet()) {
            String accNo = entry.getKey();
            Long sumAmt = entry.getValue();

            if (maxAccNo == null && maxSumAmt == null) {
                maxAccNo = accNo;
                maxSumAmt = sumAmt;
            } else {
                if (sumAmt > maxSumAmt) {
                    maxAccNo = accNo;
                    maxSumAmt = sumAmt;
                }
            }
        }

        return new AccountStatisticsInfo(year, findAccount(maxAccNo).get().getName(), maxAccNo, maxSumAmt);
    }

    /**
     * 연도별 휴면계좌 조회
     *
     * @param year 연도
     * @return 휴면계좌 목록
     */
    public List<DormantAccount> getDormantAccountsByYear(Integer year) {

        // 연도에 해당하는 거래 목록
        List<TransactionLog> filtered = transactionLogRepository.findAll().stream().filter(log -> log.getDate().startsWith(Integer.toString(year))).collect(Collectors.toList());

        List<DormantAccount> list = new ArrayList<>();

        // 계좌별 거래내역 확인 및 휴면계좌 도출
        for (Account acc : findAccounts()) {
            long transCntByAcc = filtered.stream().filter(log -> log.getAccNo().equals(acc.getAccNo())).filter(log -> "N".equals(log.getCancelYn())).count();
            if (transCntByAcc == 0L) {
                list.add(new DormantAccount(year, acc.getName(), acc.getAccNo()));
            }
        }

        return list;
    }
}
