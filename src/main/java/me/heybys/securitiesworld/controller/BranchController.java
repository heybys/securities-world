package me.heybys.securitiesworld.controller;


import me.heybys.securitiesworld.exception.BranchStatisticsNotFoundException;
import me.heybys.securitiesworld.service.BranchService;
import me.heybys.securitiesworld.vo.BranchStatistics;
import me.heybys.securitiesworld.vo.BranchStatisticsInfos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        List<BranchStatisticsInfos> list = new ArrayList<>();

        list.add(new BranchStatisticsInfos(2018, service.getBranchStatisticsByYear(2018)));
        list.add(new BranchStatisticsInfos(2019, service.getBranchStatisticsByYear(2019)));

        return list;
    }

    @GetMapping(value = "/statistics")
    public BranchStatistics getBranchStatisticsInfos(@RequestParam("brName") String brName){
        return service.getBranchStatisticsByBrName(brName).orElseThrow(()-> new BranchStatisticsNotFoundException(brName));
    }
}
