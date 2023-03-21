package jpa.jpazone.service;

import jpa.jpazone.domain.Member;
import jpa.jpazone.domain.Report;
import jpa.jpazone.domain.enumpackage.ReportHandleStatus;
import jpa.jpazone.repository.ReportRepository;
import jpa.jpazone.service.dto.ReportStatisticsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    @Transactional
    public void createReport(Member member, Long report_content_id, String reported_mem_name, String report_item, String reason) {
        log.info("[[ Service - createReportByBoard ]]");

        Report report = new Report(member, report_content_id, member.getName(), reported_mem_name, report_item, reason);

        reportRepository.saveReport(report);
    }

    public List<ReportStatisticsDto> findAllReports(){
        log.info("[[ Service - findAllReports ]]");

        List<Report> reports = reportRepository.findAllReports();

        List<ReportStatisticsDto> dtoList = reports.stream().map(ReportStatisticsDto::new).collect(Collectors.toList());

        return dtoList;
    }

    public Report findReportById(Long report_id) {
        log.info("[[ Service - findReportById ]]");
        return reportRepository.findReportById(report_id);
    }

}
