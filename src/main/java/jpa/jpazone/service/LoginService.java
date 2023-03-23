package jpa.jpazone.service;

import jpa.jpazone.domain.Member;
import jpa.jpazone.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final LoginRepository loginRepository;

    public Member findByloginId(String name, String password) {
        log.info("[[ Service - LoginService findByloginId ]]");

        Member member = loginRepository.findByloginId(name)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);

        return member;
    }

    /**
     * 현재시간과 Member 의 ban_end_time 을 비교하여
     * 로그인한 Member 의 isBanned 값 재조정.
     * @param loginMember
     */
    @Transactional
    public void validateBanMember(Member loginMember) {
        log.info("[[ Service - validateBanMember ]]");

        LocalDateTime now_date = LocalDateTime.now();
        LocalDateTime ban_end_time = loginMember.getBan_end_time();

        int num = now_date.compareTo(ban_end_time);
        // num 이 양수인 경우 현재시간이 ban_end_time 을 지난상태
        if(num > 0){
            loginMember.changeIsBanned();
        }
    }
}
