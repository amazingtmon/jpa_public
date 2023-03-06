package jpa.jpazone.service;

import jpa.jpazone.domain.Board;
import jpa.jpazone.domain.Comment;
import jpa.jpazone.domain.Member;
import jpa.jpazone.domain.Report;
import jpa.jpazone.domain.enumpackage.ReportItem;
import jpa.jpazone.domain.enumpackage.ReportReason;
import jpa.jpazone.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    @Transactional
    public void createReportByBoard(Member member, Board board, String reported_mem_name, String report_item, String reason) {
        log.info("[[ Service - createReportByBoard ]]");

        Report report = new Report(member, board, member.getName(), reported_mem_name, report_item, reason);

        reportRepository.saveReportByBoard(report);
    }

    @Transactional
    public void createReportByComment(Member member, Board board, Comment comment, String reported_mem_name, String report_item, String reason) {
        log.info("[[ Service - createReportByComment ]]");

        Report report = new Report(member, board, comment, member.getName(), reported_mem_name, report_item, reason);

        reportRepository.saveReportByComment(report);
    }
}
