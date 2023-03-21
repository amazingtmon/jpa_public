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

    @GetMapping("/api/admin-user/statistics")
    public ResponseEntity<Object> userStatistics(){
        log.info("[[ RestController - userStatistics ]]");

        List<MemberStatisticsDto> dtoList = adminService.findMemberContentUseStatistics();

        return new ResponseEntity<>(new Result<>(dtoList.size(), dtoList), HttpStatus.OK);
    }

    @GetMapping("/api/admin-report/statistics")
    public ResponseEntity<Object> reportStatistics(){
        log.info("[[ RestController - reportStatistics ]]");

        List<ReportStatisticsDto> dtoList = reportService.findAllReports();

        return new ResponseEntity<>(new Result<>(dtoList.size(), dtoList), HttpStatus.OK);
    }

    @PutMapping("/api/admin-report/status")
    public ResponseEntity<String> changeReportHandleStatus(@RequestBody ChangeStatusRequestDto changeStatusRequestDto){
        log.info("[[ RestController - changeReportHandleStatus ]]");
        log.info("dto data = > {}, {}", changeStatusRequestDto.getReport_id(), changeStatusRequestDto.getReport_handle_status());

        //Report 엔티티
        Report report = reportService.findReportById(changeStatusRequestDto.getReport_id());
        adminService.updateReportHandleStatus(report, changeStatusRequestDto.getReport_handle_status());


        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }

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
