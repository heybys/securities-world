package me.heybys.securitiesworld.service;

import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class MemberServiceTest {

//    @Autowired
//    private MemberService memberService;
//
//    @Test
//    void 회원가입() {
//        //given
//        Member member = new Member();
//        member.setName("heybys3");
//
//        //when
//        Long saveId = memberService.join(member);
//
//        //then
//        Member saveMember = null;
//        Optional<Member> one = memberService.findOne(saveId);
//        if (one.isPresent()) {
//            saveMember = one.get();
//        }
//        assertThat(member.getName()).isEqualTo(saveMember.getName());
//    }
//
//    @Test
//    void 중복_회원_가입() {
//        //given
//        Member member1 = new Member();
//        member1.setName("heybys");
//
//        Member member2 = new Member();
//        member2.setName("heybys");
//
//        //when
//        memberService.join(member1);
//        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
//
//        //then
//        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//    }
//
//    @Test
//    void findMembers() {
//    }
//
//    @Test
//    void findOne() {
//    }
}