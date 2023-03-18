package jpa.jpazone.service.dto;

import jpa.jpazone.domain.Report;
import jpa.jpazone.domain.enumpackage.ReportHandleStatus;
import jpa.jpazone.domain.enumpackage.ReportItem;
import jpa.jpazone.domain.enumpackage.ReportReason;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ReportStatisticsDto {

    private Long report_id;
    private Long member_id;
    private Long report_content_id;
    private String reporter_mem_name;
    private String reported_mem_name;
    private LocalDateTime report_time;
    private LocalDateTime report_handle_time;
    private ReportItem report_item;
    private ReportReason report_reason;
    private ReportHandleStatus report_handle_status;

    public ReportStatisticsDto (Report report){
        this.report_id = report.getId();
        this.member_id = report.getMember().getId();
        this.report_content_id = report.getReport_content_id();
        this.reporter_mem_name = report.getReporter_mem_name();
        this.reported_mem_name = report.getReported_mem_name();
        this.report_time = report.getReport_time();
        this.report_handle_time = report.getReport_handle_time();
        this.report_item = report.getReport_item();
        this.report_reason = report.getReport_reason();
        this.report_handle_status = report.getReport_handle_status();
    }
}
