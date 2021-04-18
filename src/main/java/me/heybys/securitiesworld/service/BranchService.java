package me.heybys.securitiesworld.service;

import me.heybys.securitiesworld.repository.BranchRepository;
import me.heybys.securitiesworld.vo.BranchStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchService {

    private final BranchRepository branchRepository;

    @Autowired
    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    /**
     * 연도별 관리점통계정보 조회
     *
     * @param year
     * @return
     */
    public List<BranchStatistics> getBranchStatisticsByYear(Integer year) {
        return branchRepository.findBranchStatisticsByYear(Integer.toString(year));
    }

    public Optional<BranchStatistics> getBranchStatisticsByBrName(String brName) {
        Optional<BranchStatistics> result = branchRepository.findBranchStatisticsByBrName(brName).stream().findAny();
        return result;
    }
}
