package jpa.jpazone.mybatis.service;

import jpa.jpazone.mybatis.dto.MybatisMemberDto;
import jpa.jpazone.mybatis.repository.MemberMapperRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberMapperService {
    private final MemberMapperRepository memberMapperRepository;

    /**
     * mybatis 사용해서 Member 리스트 가져오기
     * @return
     */
    public List<MybatisMemberDto> memberList(){
        log.info("[[ Mybatis memberList ]]");
        return memberMapperRepository.memberList();
    }
}
