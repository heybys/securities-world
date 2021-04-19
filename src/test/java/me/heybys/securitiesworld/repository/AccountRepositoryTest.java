package me.heybys.securitiesworld.repository;

import me.heybys.securitiesworld.entity.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("계좌정보 생성하기")
    public void save() {
        //given
        Account account = new Account("111333000000", "TestAccount", "A");

        //when
        accountRepository.save(account);
        Optional<Account> result = accountRepository.findById(account.getAccNo());

        //then
        assertThat(result).isNotEmpty();
        assertThat(result.get().getAccNo()).isEqualTo(account.getAccNo());
    }

    @Test
    @DisplayName("계좌번호로 계좌정보 조회하기")
    public void findById() {
        //given
        Account account1 = new Account("111333000001", "TestAccount1", "A");
        Account account2 = new Account("111333000002", "TestAccount2", "B");

        //when
        accountRepository.save(account1);
        accountRepository.save(account2);
        Optional<Account> result = accountRepository.findById(account1.getAccNo());

        //then
        assertThat(result).isNotEmpty();
        assertThat(result.get().getAccNo()).isEqualTo(account1.getAccNo());
    }

    @Test
    @DisplayName("전체 계좌정보 조회하기")
    public void findAll() {
        //given
        Account account1 = new Account("111333000001", "TestAccount1", "A");
        Account account2 = new Account("111333000002", "TestAccount2", "B");

        //when
        accountRepository.save(account1);
        accountRepository.save(account2);
        List<Account> result = accountRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.stream().filter(acc -> acc.getAccNo().equals(account1.getAccNo()))).isNotEmpty();
        assertThat(result.stream().filter(acc -> acc.getAccNo().equals(account2.getAccNo()))).isNotEmpty();
    }

    @Test
    @DisplayName("계좌정보 수정하기")
    public void update() {
        //given
        Account account = new Account("111333000000", "TestAccount", "A");

        //when
        accountRepository.save(account);
        account.setName("modifiedAccount");
        accountRepository.save(account);
        Optional<Account> result = accountRepository.findById(account.getAccNo());

        //then
        assertThat(result).isNotEmpty();
        assertThat(result.get().getAccNo()).isEqualTo(account.getAccNo());
        assertThat(result.get().getName()).isEqualTo(account.getName());
    }

    @Test
    @DisplayName("계좌정보 삭제하기")
    public void delete() {
        //given
        Account account = new Account("111333000000", "TestAccount", "A");

        //when
        accountRepository.save(account);
        Optional<Account> result = accountRepository.findById(account.getAccNo());
        assertThat(result).isNotEmpty();
        accountRepository.deleteById(account.getAccNo());
        result = accountRepository.findById(account.getAccNo());

        //then
        assertThat(result).isEmpty();
    }

}