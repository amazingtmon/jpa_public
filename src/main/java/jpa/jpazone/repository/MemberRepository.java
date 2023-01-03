package jpa.jpazone.repository;

import jpa.jpazone.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public Member findOne(Long id){
        log.info("[[ Repo - findOne ]]");
        return em.find(Member.class, id);
    }

    /**
     * 로그인시 member chck
     * @param username
     * @param password
     * @return
     */
    public Boolean findMemberByNamePw(String username, String password){
        log.info("[[ Repo - findOneMemberByName ]]");
        Boolean exist = false;//찾는 member의 DB data 존재 유무 판단을 위한 변수

        try{
            // 결과가 없을시 NoResultException 발생.
            Member member = em.createQuery("select m from Member m where m.name = :username" +
                            " and m.password = :password", Member.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
            log.info("member = {}", member);
            if(member != null){
                exist = true;
            }
        } catch (Exception e){
            log.error("Exception ERROR: {} ", e.getMessage());
        }
        log.info("exist = {}", exist);

        return exist;
    }

    // getSingleResult 대신 getResultList 사용.
    public List<Member> findMemberByNamePw2(String username, String password){
        log.info("[[ Repo - findOneMemberByName ]]");
        //Boolean exist = false;//찾는 member의 DB data 존재 유무 판단을 위한 변수
        List<Member> member = em.createQuery("select m from Member m where m.name = :username" +
                            " and m.password = :password", Member.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getResultList();
            log.info("member = {}, {}", member, member.getClass());

        return member;
    }

    public List<Member> findMemberByName(String username) {
        log.info("[[ Repo - findMemberByName ]]");
        return em.createQuery("select m from Member m where m.name = :username")
                .setParameter("username", username)
                .getResultList();
    }
}
