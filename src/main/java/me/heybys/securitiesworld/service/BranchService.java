package me.heybys.securitiesworld.service;

import me.heybys.securitiesworld.exception.BranchStatisticsNotFoundException;
import me.heybys.securitiesworld.repository.BranchRepository;
import me.heybys.securitiesworld.repository.TransactionLogRepository;
import me.heybys.securitiesworld.vo.BranchStatistics;
import me.heybys.securitiesworld.vo.BranchStatisticsInfos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BranchService {

    private final BranchRepository branchRepository;

    private final TransactionLogRepository transactionLogRepository;

    @Autowired
    public BranchService(BranchRepository branchRepository, TransactionLogRepository transactionLogRepository) {
        this.branchRepository = branchRepository;
        this.transactionLogRepository = transactionLogRepository;
    }

    /**
     * 모든 연도별 관리점통계정보 조회
     *
     * @return 관리점통계정보 목록
     */
    public List<BranchStatisticsInfos> getBranchStatisticsInfos() {

        List<String> years = transactionLogRepository.findTransactionLogYears();

        List<BranchStatisticsInfos> list = new ArrayList<>();
        years.forEach(year -> {
            list.add(new BranchStatisticsInfos(Integer.parseInt(year), branchRepository.findBranchStatisticsByYear(year)));
        });

        return list;
    }

    /**
     * 관지점명별 관리점통계정보 조회
     *
     * @param brName 관리점명
     * @return 관리점통계정보
     */
    public BranchStatistics getBranchStatistics(String brName) {
        return branchRepository.findBranchStatisticsByBrName(brName).stream().findAny().orElseThrow(() -> new BranchStatisticsNotFoundException(brName));
    }
}
