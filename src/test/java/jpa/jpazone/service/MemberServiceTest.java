package jpa.jpazone.service;

import jpa.jpazone.domain.Member;
import jpa.jpazone.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 이름으로Member찾기() throws Exception {
        // given
        Member member = new Member();
        member.setName("yang");

        // when
        Member yang = memberService.findMemberByName("yang");

        // then
        assertEquals(member.getName(), yang.getName());

    }

    @Test
    public void 동일아이디체크() throws Exception {
        // given
        /*중복 ID 일시*/
        Member member1 = new Member();
        member1.setName("chul");
        member1.setPassword("1234");

        /*중복아닌 ID 일시*/
        Member member2 = new Member();
        member2.setName("ysc");
        member2.setPassword("1234");

        // when
        /*중복 ID 일시*/
        Long result1 = memberService.join(member1);
        /*중복아닌 ID 일시*/
        Long result2 = memberService.join(member2);
        Member findMember = memberService.findMemberByName(member2.getName());
        Long findeMemberId = findMember.getId();

        // then
        /*중복 ID 일시*/
        //assertEquals(0L, result1);
        /*중복아닌 ID 일시*/
        assertEquals(findeMemberId, result2);
    }

}