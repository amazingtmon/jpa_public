package jpa.jpazone.api;

import jpa.jpazone.api.dto.BookmarkBoardRequestDto;
import jpa.jpazone.domain.Board;
import jpa.jpazone.domain.Member;
import jpa.jpazone.service.BoardService;
import jpa.jpazone.service.BookmarkService;
import jpa.jpazone.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookmarkApiController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final BookmarkService bookmarkService;

    @PostMapping("/api/bookmark/board")
    public ResponseEntity<Object> bookmark(@RequestBody @Valid BookmarkBoardRequestDto bookmarkBoardRequestDto,
                                           BindingResult result){
        log.info("[[ RestController - bookmark ]]");
        log.info("board_id , member_id => {}, {}", bookmarkBoardRequestDto.getBoard_id(), bookmarkBoardRequestDto.getMember_name());

        if(result.hasErrors()){
            log.info("bookmark errors => {}", result.getFieldErrors());
            return new ResponseEntity<>(result.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        //Member 엔티티
        Member member = memberService.findMemberByName(bookmarkBoardRequestDto.getMember_name());
        //Board 엔티티
        Board board = boardService.findBoard(bookmarkBoardRequestDto.getBoard_id());
        bookmarkService.createBookmark(board, member);

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}
