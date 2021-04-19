package me.heybys.securitiesworld.controller;


import me.heybys.securitiesworld.service.BranchService;
import me.heybys.securitiesworld.vo.BranchStatistics;
import me.heybys.securitiesworld.vo.BranchStatisticsInfos;
import me.heybys.securitiesworld.vo.BranchIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/branch")
public class BranchController {

    private final BranchService service;

    @Autowired
    public BranchController(BranchService service) {
        this.service = service;
    }

    @GetMapping(value = "/statistics/year")
    public List<BranchStatisticsInfos> getBranchStatisticsInfos() {
        return service.getBranchStatisticsInfos();
    }

    @GetMapping(value = "/statistics")
    public BranchStatistics getBranchStatistics(@RequestParam("brName") String brName) {
        return service.getBranchStatistics(brName);
    }

    @PostMapping(value = "/merge")
    public Boolean mergeBranch(@RequestBody() BranchIntegration branchIntegration) {
        return service.mergeBranch(branchIntegration);
    }
}
