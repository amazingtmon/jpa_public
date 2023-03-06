package jpa.jpazone.api;

import jpa.jpazone.api.dto.ReportItemRequestDto;
import jpa.jpazone.controller.SessionConstants;
import jpa.jpazone.domain.Board;
import jpa.jpazone.domain.Comment;
import jpa.jpazone.domain.Member;
import jpa.jpazone.service.BoardService;
import jpa.jpazone.service.CommentService;
import jpa.jpazone.service.MemberService;
import jpa.jpazone.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReportApiController {

    private final ReportService reportService;
    private final MemberService memberService;
    private final BoardService boardService;
    private final CommentService commentService;

    @PostMapping("/api/report-board")
    public ResponseEntity<Object> reportBoard(@RequestBody ReportItemRequestDto reportItemRequestDto,
                                         @SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member loginMember){
        log.info("[[ RestController - reportBoard ]]");

        //Board 엔티티
        Board board = boardService.findBoard(reportItemRequestDto.getId());
        //Member 엔티티
        Member member = memberService.findMemberById(loginMember.getId());
        //Report 엔티티 생성
        reportService.createReportByBoard(member, board, reportItemRequestDto.getReported_mem_name(), reportItemRequestDto.getReport_item() ,reportItemRequestDto.getReason());


        return new ResponseEntity<>("ok", HttpStatus.CREATED);
    }

    @PostMapping("/api/report-comment")
    public ResponseEntity<Object> reportComment(@RequestBody ReportItemRequestDto reportItemRequestDto,
                                         @SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member loginMember){
        log.info("[[ RestController - reportComment ]]");

        //Comment 엔티티
        Comment comment = commentService.findCommentById(reportItemRequestDto.getId());
        //Board 엔티티
        Board board = comment.getBoard();
        //Member 엔티티
        Member member = memberService.findMemberById(loginMember.getId());
        //Report 엔티티 생성
        reportService.createReportByComment(member, board, comment, reportItemRequestDto.getReported_mem_name(), reportItemRequestDto.getReport_item(), reportItemRequestDto.getReason());


        return new ResponseEntity<>("ok", HttpStatus.CREATED);
    }
}
