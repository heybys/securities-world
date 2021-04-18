package me.heybys.securitiesworld.batchprocessing;

import me.heybys.securitiesworld.entity.TransactionLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class TransactionLogItemProcessor implements ItemProcessor<TransactionLog, TransactionLog> {

    private static final Logger log = LoggerFactory.getLogger(TransactionLogItemProcessor.class);

    @Override
    public TransactionLog process(final TransactionLog transactionLog) throws Exception {
        final String date = transactionLog.getDate();
        final String accNo = transactionLog.getAccNo();
        final Long transNo = transactionLog.getTransNo();
        final Long amount = transactionLog.getAmount();
        final Long fee = transactionLog.getFee();
        final String cancelYn = transactionLog.getCancelYn();

        final TransactionLog transformedTransactionLog = new TransactionLog(date, accNo, transNo, amount, fee, cancelYn);

        return transformedTransactionLog;
    }

}
