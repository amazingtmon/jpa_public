package jpa.jpazone.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

    private String reporter_mem_name; // 신고한 유저
    private String reported_mem_name; // 신고당한 유저
    private LocalDateTime report_time; // 신고한 시간
    private LocalDateTime report_handle_time; // 신고처리한 시간
    // 신고된 컨텐츠 종류
    // 신고 이유
}
