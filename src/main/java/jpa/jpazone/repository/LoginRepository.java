package jpa.jpazone.repository;

import jpa.jpazone.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LoginRepository {

    private final EntityManager em;

    /*
        로그인 시도한 유저의 아이디가 없을경우를 대비해 return해주는 Member 엔티티를 Optional로 감싸주어 처리
     */
    public Optional<Member> findByloginId(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        Optional<Member> member = result.stream().filter(m -> m.getName().equals(name))
                .findFirst();

        return member;
    }
}
