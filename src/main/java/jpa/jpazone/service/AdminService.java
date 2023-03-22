package jpa.jpazone.service;

import jpa.jpazone.domain.Member;
import jpa.jpazone.domain.Report;
import jpa.jpazone.domain.enumpackage.ReportHandleStatus;
import jpa.jpazone.repository.*;
import jpa.jpazone.service.dto.ContentsCountDto;
import jpa.jpazone.service.dto.MemberStatisticsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final MemberService memberService;

    /**
     * 관리자 메인 페이지
     * @return
     */
    public List<ContentsCountDto> findAllContentsCount(){
        log.info("[[ Service - findAllContentsCount ]]");

        List<ContentsCountDto> dtoList = new ArrayList<>();
        //모든 컨텐츠들의 갯수 count
        List<Object[]> dataList = adminRepository.findAllContentsCount();
        dataList.forEach( data -> {
            // Bigint -> int 로 변환 후 dto 에 담기
            int memberCount = Integer.parseInt(String.valueOf(data[0]));
            int boardsCount = Integer.parseInt(String.valueOf(data[1]));
            int commentsCount = Integer.parseInt(String.valueOf(data[2]));
            int bookmarksCount = Integer.parseInt(String.valueOf(data[3]));
            int newsCount = Integer.parseInt(String.valueOf(data[4]));
            int reportsCount = Integer.parseInt(String.valueOf(data[5]));
            ContentsCountDto dto = new ContentsCountDto(memberCount, boardsCount, commentsCount, bookmarksCount, newsCount, reportsCount);
            dtoList.add(dto);
        });

        return dtoList;
    }

    /**
     * 멤버들의 컨텐츠 사용 통계
     * @return
     */
    public List<MemberStatisticsDto> findMemberContentUseStatistics(){
        log.info("[[ Service - findMemberContentUseStatistics ]]");

        List<MemberStatisticsDto> dtoList = new ArrayList<>();
        //멤버들의 컨텐츠 사용 횟수들 조회
        List<Object[]> dataList = adminRepository.findMemberContentUseStatistics();
        dataList.forEach( data -> {
            // Bigint -> int 로 변환 후 dto 에 담기
            int user_id = Integer.parseInt(String.valueOf(data[0]));
            String name = String.valueOf(data[1]);
            int board_count = Integer.parseInt(String.valueOf(data[2]));
            int comment_count = Integer.parseInt(String.valueOf(data[3]));
            int bookmark_count = Integer.parseInt(String.valueOf(data[4]));
            int news_count = Integer.parseInt(String.valueOf(data[5]));
            int report_count = Integer.parseInt(String.valueOf(data[6]));
            MemberStatisticsDto dto = new MemberStatisticsDto(user_id, name, board_count, comment_count,
                                    bookmark_count ,news_count, report_count);
            dtoList.add(dto);
        });

        return dtoList;
    }

    @Transactional
    public void updateReportHandleStatus(Report report, String report_handle_status) {
        log.info("[[ Service - updateReportHandleStatus ]]");

        ReportHandleStatus status = ReportHandleStatus.valueOf(report_handle_status);

        if(status.equals(ReportHandleStatus.COMPLETE)){
            processBanMember(report, status);
            return;
        }

        //Report 엔티티 report_handle_status 값 변경
        report.changeStatus(status);
    }

    private void processBanMember(Report report, ReportHandleStatus status) {
        //Report 엔티티 report_handle_status 값 변경
        report.changeStatus(status);
        //신고된 멤버 엔티티 찾기
        String reported_mem_name = report.getReported_mem_name();
        Member member = memberService.findMemberByName(reported_mem_name);
        //멤버의 컨텐츠 사용권한 금지 프로세스
        member.decideBanMember();
    }

    @Transactional
    public int updateAllReportsHandleStatus(String report_handle_status, List<Long> reportIdArray) {
        log.info("[[ Service - updateAllReportsHandleStatus ]]");
        ReportHandleStatus status = ReportHandleStatus.valueOf(report_handle_status);

        int result = adminRepository.updateAllReportsHandleStatus(status, reportIdArray);

        return result;
    }
}
