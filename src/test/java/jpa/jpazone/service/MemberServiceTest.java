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

}