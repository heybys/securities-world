package me.heybys.securitiesworld.batchprocessing;

import me.heybys.securitiesworld.entity.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class AccountItemProcessor implements ItemProcessor<Account, Account> {

    private static final Logger log = LoggerFactory.getLogger(AccountItemProcessor.class);

    @Override
    public Account process(final Account account) throws Exception {
        final String accNo = account.getAccNo();
        final String name = account.getName();
        final String brCode = account.getBrCode();

        final Account transformedAccount = new Account(accNo, name, brCode);

        return transformedAccount;
    }

}
