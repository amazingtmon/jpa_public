package jpa.jpazone.api;

import jpa.jpazone.api.dto.admin.ChangeStatusRequestDto;
import jpa.jpazone.api.dto.admin.ReportsArrayChangeStatusDto;
import jpa.jpazone.domain.Report;
import jpa.jpazone.service.ReportService;
import jpa.jpazone.service.dto.MemberStatisticsDto;
import jpa.jpazone.service.AdminService;
import jpa.jpazone.service.dto.ReportStatisticsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminApiController {

    private final AdminService adminService;
    private final ReportService reportService;

    /**
     * 유저들의 각 컨텐츠 사용량 확인
     * @return
     */
    @GetMapping("/api/admin-user/statistics")
    public ResponseEntity<Object> userStatistics(){
        log.info("[[ RestController - userStatistics ]]");

        List<MemberStatisticsDto> dtoList = adminService.findMemberContentUseStatistics();

        return new ResponseEntity<>(new Result<>(dtoList.size(), dtoList), HttpStatus.OK);
    }

    /**
     * 유저들이 신고한 신고건 데이터
     * @return
     */
    @GetMapping("/api/admin-report/statistics")
    public ResponseEntity<Object> reportStatistics(){
        log.info("[[ RestController - reportStatistics ]]");

        List<ReportStatisticsDto> dtoList = reportService.findAllReports();

        return new ResponseEntity<>(new Result<>(dtoList.size(), dtoList), HttpStatus.OK);
    }

    /**
     * 신고 상태 변경 (Report_Handle_Status)
     * @param changeStatusRequestDto
     * @return
     */
    @PutMapping("/api/admin-report/status")
    public ResponseEntity<String> changeReportHandleStatus(@RequestBody ChangeStatusRequestDto changeStatusRequestDto){
        log.info("[[ RestController - changeReportHandleStatus ]]");

        //Report 엔티티
        Report report = reportService.findReportById(changeStatusRequestDto.getReport_id());
        adminService.updateReportHandleStatus(report, changeStatusRequestDto.getReport_handle_status());

        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }

    /**
     * 체크박스로 1개 이상 신고건들의 상태 변경
     * @param reportsArrayChangeStatusDto
     * @return
     */
    @PutMapping("/api/admin-report/status-all")
    public int changeAllReportsHandleStatus(@RequestBody ReportsArrayChangeStatusDto reportsArrayChangeStatusDto){
        log.info("[[ RestController - changeAllReportsHandleStatus ]]");

        int result = adminService.updateAllReportsHandleStatus(reportsArrayChangeStatusDto.getReport_handle_status(),
                reportsArrayChangeStatusDto.getReportIdArray());

        return result;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }
}
