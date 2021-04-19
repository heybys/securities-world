package me.heybys.securitiesworld.repository;

import me.heybys.securitiesworld.entity.Branch;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BranchRepositoryTest {

    @Autowired
    private BranchRepository branchRepository;

    @Test
    @DisplayName("관리점 생성하기")
    public void save() {
        //given
        Branch branch = new Branch("A", "판교점");

        //when
        branchRepository.save(branch);
        Optional<Branch> result = branchRepository.findById(branch.getBrCode());

        //then
        assertThat(result).isNotEmpty();
        assertThat(result.get().getBrCode()).isEqualTo(branch.getBrCode());
    }

    @Test
    @DisplayName("관리점코드로 관리점 조회하기")
    public void findById() {
        //given
        Branch branch1 = new Branch("A", "판교점");
        Branch branch2 = new Branch("B", "동탄점");

        //when
        branchRepository.save(branch1);
        branchRepository.save(branch2);
        Optional<Branch> result = branchRepository.findById(branch1.getBrCode());

        //then
        assertThat(result).isNotEmpty();
        assertThat(result.get().getBrCode()).isEqualTo(branch1.getBrCode());
    }

    @Test
    @DisplayName("전체 관리점 조회하기")
    public void findAll() {
        //given
        Branch branch1 = new Branch("A", "판교점");
        Branch branch2 = new Branch("B", "동탄점");

        //when
        branchRepository.save(branch1);
        branchRepository.save(branch2);
        List<Branch> result = branchRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.stream().filter(br -> br.getBrCode().equals(branch1.getBrCode()))).isNotEmpty();
        assertThat(result.stream().filter(br -> br.getBrCode().equals(branch2.getBrCode()))).isNotEmpty();
    }

    @Test
    @DisplayName("관리점 수정하기")
    public void update() {
        //given
        Branch branch = new Branch("A", "판교점");

        //when
        branchRepository.save(branch);
        branch.setBrName("용산점");
        branchRepository.save(branch);
        Optional<Branch> result = branchRepository.findById(branch.getBrCode());

        //then
        assertThat(result).isNotEmpty();
        assertThat(result.get().getBrCode()).isEqualTo(branch.getBrCode());
        assertThat(result.get().getBrName()).isEqualTo(branch.getBrName());
    }

    @Test
    @DisplayName("관리점 삭제하기")
    public void delete() {
        //given
        Branch branch = new Branch("A", "판교점");

        //when
        branchRepository.save(branch);
        Optional<Branch> result = branchRepository.findById(branch.getBrCode());
        assertThat(result).isNotEmpty();
        branchRepository.deleteById(branch.getBrCode());
        result = branchRepository.findById(branch.getBrCode());

        //then
        assertThat(result).isEmpty();
    }

}