package jpa.jpazone.service;

import jpa.jpazone.domain.Member;
import jpa.jpazone.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {

    private final LoginRepository loginRepository;

    public Member findByloginId(String name, String password) {
        log.info("[[ Service - LoginService findByloginId ]]");

        Member member = loginRepository.findByloginId(name)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);

        return member;
    }
}
