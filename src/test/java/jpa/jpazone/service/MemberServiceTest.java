package jpa.jpazone.service;

import jpa.jpazone.domain.Member;
import jpa.jpazone.domain.MemberRole;
import jpa.jpazone.domain.enumpackage.RoleGroup;
import jpa.jpazone.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public void 회원가입() throws Exception {
        // given
        String name = "yang";
        String password = "123";

        // when
        Long newMemberId = memberService.join(name, password);
        Member member = memberRepository.findOne(newMemberId);
        System.out.println("member name = "+member.getName());
        List<MemberRole> memberRoles = member.getMemberRoles();
        memberRoles.forEach(mr-> {
            System.out.println("mr get = "+mr.getRole());
        });
        // then
        assertEquals("yang", member.getName());
    }
    @Test
    public void 동일아이디체크() throws Exception {
        // given
        /*중복 ID 일시*/
        Member member1 = new Member("chul","1234");

        /*중복아닌 ID 일시*/
        Member member2 = new Member("ysc", "1234");

        // when
        /*중복 ID 일시*/
        Long result1 = memberService.join(member1.getName(), member1.getPassword());
        /*중복아닌 ID 일시*/
        Long result2 = memberService.join(member2.getName(), member2.getPassword());
        Member findMember = memberService.findMemberByName(member2.getName());
        Long findeMemberId = findMember.getId();

        // then
        /*중복 ID 일시*/
        //assertEquals(0L, result1);
        /*중복아닌 ID 일시*/
        assertEquals(findeMemberId, result2);
    }

}