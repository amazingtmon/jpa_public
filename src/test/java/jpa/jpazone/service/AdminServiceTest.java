package jpa.jpazone.service;

import jpa.jpazone.repository.*;
import jpa.jpazone.service.dto.MemberStatisticsDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AdminServiceTest {

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    AdminService adminService;

    @Test
    public void 멤버컨텐츠사용량테스트() throws Exception {
        // given

        // when
        List<Object[]> result = adminRepository.findMemberContentUseStatistics();
        System.out.println("result size = "+result.size());
        List<MemberStatisticsDto> dtoList = new ArrayList<>();
        for(Object[] r:result){
            System.out.println("r length = "+r.length);
            System.out.println(r[0]+","+r[1]+","+r[2]+","+r[3]);
            int user_id = Integer.parseInt(String.valueOf(r[0]));
            String name = String.valueOf(r[1]);
            int board_count = Integer.parseInt(String.valueOf(r[2]));
            int comment_count = Integer.parseInt(String.valueOf(r[3]));
            int bookmark_count = Integer.parseInt(String.valueOf(r[4]));
            int news_count = Integer.parseInt(String.valueOf(r[5]));
            int report_count = Integer.parseInt(String.valueOf(r[6]));
            MemberStatisticsDto dto = new MemberStatisticsDto(user_id, name, board_count, comment_count,
                    bookmark_count ,news_count, report_count);
            dtoList.add(dto);
        }

        dtoList.forEach( dtos -> {
            System.out.println(dtos.getUser_id()+", "+dtos.getName());
        });

        // then
    }

    @Test
    public void 각컨텐츠들갯수() throws Exception {
        // given
        // when
        List<Object[]> result = adminRepository.findAllContentsCount();
        System.out.println("result size, result.get(0).length = "+result.size()+", "+result.get(0).length);

        Object[] arr1 = Arrays.stream(result.get(0)).toArray();
        for(Object r:arr1){
            System.out.println(r+", "+r.getClass());
        }
/*        result.forEach( r -> {
            System.out.println("r -> "+r[0]+","+r[1]+","+r[2]+","+r[3]);
        });*/

        // then
    }
}