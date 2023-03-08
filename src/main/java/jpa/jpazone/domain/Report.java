package jpa.jpazone.domain;

import jpa.jpazone.domain.enumpackage.ReportHandleStatus;
import jpa.jpazone.domain.enumpackage.ReportItem;
import jpa.jpazone.domain.enumpackage.ReportReason;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report {

    @Id
    @GeneratedValue
    @Column(name = "report_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    private String reporter_mem_name; // 신고한 유저
    private String reported_mem_name; // 신고당한 유저
    private LocalDateTime report_time; // 신고한 시간
    private LocalDateTime report_handle_time; // 신고처리한 시간
    @Enumerated(EnumType.STRING) // 신고된 컨텐츠 종류 (BOARD, COMMENT)
    private ReportItem report_item;
    @Enumerated(EnumType.STRING) // 신고 이유
    private ReportReason report_reason;
    @Enumerated(EnumType.STRING) // 신고된 컨텐츠 처리 상태
    private ReportHandleStatus report_handle_status;

    //==연관관계 메서드==//
    public void setMember(Member member){
        this.member = member;
        member.getReports().add(this);
    }

    public void setBoard(Board board){
        this.board = board;
        board.getReports().add(this);
    }

    public void setComment(Comment comment){
        this.comment = comment;
        comment.getReports().add(this);
    }

    //게시글 신고
    public Report(Member member, Board board, String name, String reported_mem_name, String report_item, String reason){
        this.setMember(member);
        this.setBoard(board);
        this.reporter_mem_name = name;
        this.reported_mem_name = reported_mem_name;
        this.report_time = LocalDateTime.now();
        this.report_item = ReportItem.valueOf(report_item);
        this.report_reason = ReportReason.valueOf(reason);
        this.report_handle_status = ReportHandleStatus.REPORTED;
    }

    //댓글 & 대댓글 신고
    public Report(Member member, Board board, Comment comment, String name, String reported_mem_name, String report_item, String reason) {
        this.setMember(member);
        this.setBoard(board);
        this.setComment(comment);
        this.reporter_mem_name = name;
        this.reported_mem_name = reported_mem_name;
        this.report_time = LocalDateTime.now();
        this.report_item = ReportItem.valueOf(report_item);
        this.report_reason = ReportReason.valueOf(reason);
        this.report_handle_status = ReportHandleStatus.REPORTED;
    }

    //==비즈니스 메서드==//
}
