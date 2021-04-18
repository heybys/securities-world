package me.heybys.securitiesworld.repository;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class SpringDataJpaMemberRepositoryTest {

//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Test
//    public void save() {
//        Member member = new Member();
//        member.setName("heybys");
//
//        memberRepository.save(member);
//
//        Member saved = null;
//
//        Optional<Member> result = memberRepository.findById(member.getId());
//        if (result.isPresent()) {
//            saved = result.get();
//        }
//
//        assertThat(member).isEqualTo(saved);
//    }
//
//    @Test
//    public void findById() {
//        Member member1 = new Member();
//        member1.setName("heybys1");
//        memberRepository.save(member1);
//
//        Member member2 = new Member();
//        member2.setName("heybys2");
//        memberRepository.save(member2);
//
//        Member saved = null;
//
//        Optional<Member> result = memberRepository.findById(member1.getId());
//        if (result.isPresent()) {
//            saved = result.get();
//        }
//
//        assertThat(member1).isEqualTo(saved);
//    }
//
//    @Test
//    public void findByName() {
//        Member member1 = new Member();
//        member1.setName("heybys1");
//        memberRepository.save(member1);
//
//        Member member2 = new Member();
//        member2.setName("heybys2");
//        memberRepository.save(member2);
//
//        Member saved = null;
//
//        Optional<Member> result = memberRepository.findByName("heybys1");
//        if (result.isPresent()) {
//            saved = result.get();
//        }
//
//        assertThat(member1).isEqualTo(saved);
//    }
//
//    @Test
//    public void findAll() {
//        Member member1 = new Member();
//        member1.setName("heybys1");
//        memberRepository.save(member1);
//
//        Member member2 = new Member();
//        member2.setName("heybys2");
//        memberRepository.save(member2);
//
//        List<Member> result = memberRepository.findAll();
//
//        assertThat(result.size()).isEqualTo(2);
//    }

}
