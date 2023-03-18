package jpa.jpazone.service;

import jpa.jpazone.domain.enumpackage.ReportItem;
import jpa.jpazone.domain.enumpackage.ReportReason;
import jpa.jpazone.repository.ReportRepository;
import jpa.jpazone.service.dto.ReportStatisticsDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ReportServiceTest {

    @Autowired
    ReportService reportService;
    @Autowired
    ReportRepository reportRepository;

    @Test
    public void 신고이유테스트() throws Exception {
        // given
        ReportReason result = ReportReason.valueOf("ADULT");
        ReportReason[] values = ReportReason.values();

        ReportItem board = ReportItem.valueOf("BOARD");

        // when
        System.out.println("result = "+result+", "+result.getClass());
        System.out.println("ReportItem = "+board+", "+board.getClass());

        // then
    }

    @Test
    public void 신고엔티티데이터가져오기() throws Exception {
        // given
        // when
        int report_count = reportRepository.findAllReportsCount();
        List<ReportStatisticsDto> allReports = reportService.findAllReports();
        allReports.stream().forEach(dto -> {
            System.out.println(" id = "+dto.getReport_id());
        });

        // then
        assertEquals(report_count, allReports.size());
    }
}