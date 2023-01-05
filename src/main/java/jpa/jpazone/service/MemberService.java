package jpa.jpazone.service;

import jpa.jpazone.domain.Member;
import jpa.jpazone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
//읽기 전용. 단순히 DB data를 select해서 결과를 가져오는 Transaction 에서는 readOnly = true를 사용해주자.
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public Member findMemberByName(String username){
        log.info("[[ Service - findMemberByName ]]");
        List<Member> members = memberRepository.findMemberByName(username);
        /*
            DB에 없는 member를 입력할 경우 IndexOutOfBoundsException 이 일어난다.
            members의 size를 체크한후 size가 1보다 작은경우 회원가입 페이지로 이동시켜
            회원가입을 할 수 있도록 해보자.
         */
        Long id = members.get(0).getId();
        Member member = memberRepository.findOne(id);
        return member;
    }

    public Boolean findMemberByNamePw(String username, String password){
        log.info("[[ Service - findOneMemberByName ]]");
        Boolean result = memberRepository.findMemberByNamePw(username, password);

        log.info("findOneMemberByName result = {}", result);

        return result;
    }

    public Boolean findMemberByNamePw2(String username, String password){
        log.info("[[ Service - findOneMemberByName ]]");
        List<Member> member = memberRepository.findMemberByNamePw2(username, password);
        if(member.size() < 1){
            return false;
        }

        log.info("findOneMemberByName member = {}", member);

        return true;
    }


}