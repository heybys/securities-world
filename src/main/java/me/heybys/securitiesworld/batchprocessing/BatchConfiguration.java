package me.heybys.securitiesworld.batchprocessing;

import me.heybys.securitiesworld.entity.Account;
import me.heybys.securitiesworld.entity.Branch;
import me.heybys.securitiesworld.entity.TransactionLog;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public FlatFileItemReader<Branch> branchReader() {
        return new FlatFileItemReaderBuilder<Branch>()
                .name("branchItemReader")
                .resource(new ClassPathResource("과제1_데이터_관리점정보.csv"))
                .linesToSkip(1)
                .delimited()
                .names(new String[]{"brCode", "brName"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Branch>() {{
                    setTargetType(Branch.class);
                }})
                .build();
    }

    @Bean
    public FlatFileItemReader<Account> accountReader() {
        return new FlatFileItemReaderBuilder<Account>()
                .name("accountItemReader")
                .resource(new ClassPathResource("과제1_데이터_계좌정보.csv"))
                .linesToSkip(1)
                .delimited()
                .names(new String[]{"accNo", "name", "brCode"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Account>() {{
                    setTargetType(Account.class);
                }})
                .build();
    }

    @Bean
    public FlatFileItemReader<TransactionLog> transactionLogReader() {
        return new FlatFileItemReaderBuilder<TransactionLog>()
                .name("transactionLogItemReader")
                .resource(new ClassPathResource("과제1_데이터_거래내역.csv"))
                .linesToSkip(1)
                .delimited()
                .names(new String[]{"date", "accNo", "transNo", "amount", "fee", "cancelYn"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<TransactionLog>() {{
                    setTargetType(TransactionLog.class);
                }})
                .build();
    }

    @Bean
    public BranchItemProcessor branchProcessor() {
        return new BranchItemProcessor();
    }

    @Bean
    public AccountItemProcessor accountProcessor() {
        return new AccountItemProcessor();
    }

    @Bean
    public TransactionLogItemProcessor transactionLogProcessor() {
        return new TransactionLogItemProcessor();
    }

    @Bean
    public JpaItemWriter<Branch> branchWriter(DataSource dataSource) {
        JpaItemWriter<Branch> jpaItemWriter = new JpaItemWriter<Branch>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

    @Bean
    public JpaItemWriter<Account> accountWriter(DataSource dataSource) {
        JpaItemWriter<Account> jpaItemWriter = new JpaItemWriter<Account>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

    @Bean
    public JpaItemWriter<TransactionLog> transactionLogWriter(DataSource dataSource) {
        JpaItemWriter<TransactionLog> jpaItemWriter = new JpaItemWriter<TransactionLog>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1, Step step2, Step step3) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .next(step2)
                .next(step3)
                .end()
                .build();
    }

    @Bean
    public Step step1(JpaItemWriter<Branch> branchWriter) {
        return stepBuilderFactory.get("Branch Data Initialization")
                .<Branch, Branch>chunk(10)
                .reader(branchReader())
                .processor(branchProcessor())
                .writer(branchWriter)
                .build();
    }

    @Bean
    public Step step2(JpaItemWriter<Account> accountWriter) {
        return stepBuilderFactory.get("Account Data Initialization")
                .<Account, Account>chunk(10)
                .reader(accountReader())
                .processor(accountProcessor())
                .writer(accountWriter)
                .build();
    }

    @Bean
    public Step step3(JpaItemWriter<TransactionLog> transactionLogWriter) {
        return stepBuilderFactory.get("Transaction Log Data Initialization")
                .<TransactionLog, TransactionLog>chunk(10)
                .reader(transactionLogReader())
                .processor(transactionLogProcessor())
                .writer(transactionLogWriter)
                .build();
    }
}