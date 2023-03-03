package jpa.jpazone.domain;

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
    // 신고된 컨텐츠 종류
    @Enumerated(EnumType.STRING)
    private ReportItem report_item;
    // 신고 이유
    @Enumerated(EnumType.STRING)
    private ReportReason report_reason;

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

    //==비즈니스 메서드==//
}
