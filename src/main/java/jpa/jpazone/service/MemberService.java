package jpa.jpazone.service;

import jpa.jpazone.domain.Member;
import jpa.jpazone.domain.MemberRole;
import jpa.jpazone.domain.enumpackage.RoleGroup;
import jpa.jpazone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
//읽기 전용. 단순히 DB data를 select해서 결과를 가져오는 Transaction 에서는 readOnly = true를 사용해주자.
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public Member findMemberById(Long id) {
        log.info("[[ findMemberById ]]");

        return memberRepository.findOne(id);
    }

    public Member findMemberByName(String username){
        log.info("[[ Service - findMemberByName ]]");
        List<Member> members = memberRepository.findMemberByName(username);
        /*
            DB에 없는 member를 입력할 경우 IndexOutOfBoundsException 이 일어난다.
            members의 size를 체크한후 size가 1보다 작은경우 회원가입 페이지로 이동시켜
            회원가입을 할 수 있도록 해보자.
            ===================================================================
            2023-02-13 stream 을 활용하여 member 엔티티 확인.
         */
//        Long id = members.get(0).getId();
//        Member member = memberRepository.findOne(id);
        Member member = members.stream().filter(m -> m.getName().equals(username))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다."));

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

    @Transactional
    public Long join(String name, String password) {
        log.info("[[ service - findMember ]]");

        //name 중복 체크
        if(!validateDupeMember(name)){
            Long existDupeMember = 0L;
            return existDupeMember;
        }
        //Member 엔티티 생성
        if(name.equals("jpa_admin")){//관리자 계정
            Member member = new Member(name, password);
            MemberRole memberRole = new MemberRole(member, RoleGroup.ADMIN);
            memberRole.setMember(member);
            memberRepository.join(member);
            return member.getId();
        }

        //일반 유저
        Member member = new Member(name, password);
        MemberRole memberRole = new MemberRole(member, RoleGroup.USER);
        memberRole.setMember(member);
        memberRepository.join(member);

        return member.getId();
    }

    /**
     * 회원가입시 동일 ID 유효성 검사
     * @param name
     * @return 동일 ID 존재시 false return
     */
    private boolean validateDupeMember(String name) {
        List<Member> findMember = memberRepository.findMemberByName(name);
        //동일 name 의 member가 존재하면 false
        if (!findMember.isEmpty()) {
            log.info("member = {}", findMember);
            //throw new IllegalStateException("이미 존재하는 회원입니다.");

            return false;
        }
        return true;
    }

    @Transactional
    public void updateMemberInfo(String name, String password) {
        log.info("[[ service - updateMemberInfo ]]");
        //이름으로 member 찾기
        Long id = getMemberIdByName(name);
        //memberInfo 변경할 member
        Member member = memberRepository.findOne(id);
        //member info 변경
        member.change(password);
    }

    private Long getMemberIdByName(String name) {
        List<Member> memberByName = memberRepository.findMemberByName(name);
        Optional<Member> optionalMember = memberByName.stream().findFirst();

        //member id 확인
        if(optionalMember.isPresent()){
            Long id = optionalMember.get().getId();
            return id;
        }

        return null;
    }
}
