package me.heybys.securitiesworld.controller;


import me.heybys.securitiesworld.entity.Account;
import me.heybys.securitiesworld.service.AccountService;
import me.heybys.securitiesworld.vo.AccountStatisticsInfo;
import me.heybys.securitiesworld.vo.DormantAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    private final AccountService service;

    @Autowired
    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping(value = "/statistics")
    public List<AccountStatisticsInfo> getAccountStatisticsInfos() {
        List<AccountStatisticsInfo> list = new ArrayList<>();

        list.add(service.getAccountStatisticsInfoByYear(2018));
        list.add(service.getAccountStatisticsInfoByYear(2019));

        return list;
    }

    @GetMapping(value = "/dormancy")
    public List<DormantAccount> getDormantAccounts() {
        List<DormantAccount> list = new ArrayList<>();

        list.addAll(service.getDormantAccountsByYear(2018));
        list.addAll(service.getDormantAccountsByYear(2019));

        return list;
    }

}
