package jpa.jpazone.service;

import jpa.jpazone.controller.form.MemberInfoDto;
import jpa.jpazone.domain.Member;
import jpa.jpazone.domain.MemberRole;
import jpa.jpazone.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

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

    @Test
    public void 시간비교테스트() throws Exception {
        // given
        LocalDateTime ban_end_time = LocalDateTime.of(2023, 3, 23, 22, 30, 50);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime clock = LocalDateTime.now(Clock.systemDefaultZone());
        LocalDateTime zone = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        int result = now.compareTo(ban_end_time);

        System.out.println("now = "+now+" // ban_end_time = "+ban_end_time);
        System.out.println("clock = "+clock+" // zone = "+zone);
        System.out.println("result = "+result);
        // when

        // then
    }

    @Test
    public void MemberRole_확인하기() throws Exception {
        // given
        Member member = memberService.findMemberById(1L);
        MemberInfoDto memberInfoDto = memberService.transformToMemberInfoDto(member);
        System.out.println("member role => "+memberInfoDto.getRole());
        // when

        // then
    }

}