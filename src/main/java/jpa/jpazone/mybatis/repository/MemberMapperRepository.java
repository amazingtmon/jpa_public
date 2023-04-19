package jpa.jpazone.mybatis.repository;

import jpa.jpazone.mybatis.dto.MybatisMemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MemberMapperRepository {
    List<MybatisMemberDto> memberList();
}
