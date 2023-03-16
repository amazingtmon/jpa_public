package jpa.jpazone.api;

import jpa.jpazone.service.dto.MemberStatisticsDto;
import jpa.jpazone.service.AdminService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminApiController {

    private final AdminService adminService;

    @GetMapping("/api/admin-user/statistics")
    public ResponseEntity<Object> userStatistics(){
        log.info("[[ RestController - userStatistics ]]");

        List<MemberStatisticsDto> dtoList = adminService.findMemberContentUseStatistics();

        return new ResponseEntity<>(new Result<>(dtoList.size(), dtoList), HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }
}