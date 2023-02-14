package jpa.jpazone.api;

import jpa.jpazone.api.dto.BookmarkBoardRequestDto;
import jpa.jpazone.domain.Board;
import jpa.jpazone.domain.Bookmark;
import jpa.jpazone.domain.Member;
import jpa.jpazone.service.BoardService;
import jpa.jpazone.service.BookmarkService;
import jpa.jpazone.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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

        if(result.hasErrors()){
            log.info("bookmark errors => {}", result.getFieldErrors());
            return new ResponseEntity<>(result.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        //Member 엔티티
        Member member = memberService.findMemberByName(bookmarkBoardRequestDto.getMember_name());
        //Board 엔티티
        Board board = boardService.findBoard(bookmarkBoardRequestDto.getBoard_id());
        //Bookmark 테이블에 데이터 존재여부 체크
        Optional<Bookmark> optionalBookmark = bookmarkService.findBookmarkByBoardIdAndMemberId(bookmarkBoardRequestDto.getBoard_id(), member.getId());
        if(optionalBookmark.isPresent()){
            Bookmark bookmark = optionalBookmark.get();
            //Bookmark 테이블에 data 가 존재하면서, isBookMarked 값이 true 일 경우
            if(bookmark.isBookMarked()){
                return new ResponseEntity<>("이미 북마크가 완료된 게시물입니다.", HttpStatus.BAD_REQUEST);
            }

            //다시 북마크 진행하여 isBookMarked 값을 true 로 update
            bookmarkService.updateBookmark(bookmark);
            return new ResponseEntity<>("update", HttpStatus.ACCEPTED);
        }

        bookmarkService.createBookmark(board, member);

        return new ResponseEntity<>("ok", HttpStatus.ACCEPTED);
    }

    @PutMapping("/api/bookmark/board")
    public ResponseEntity<Object> bookmarkCancel(@RequestBody @Valid BookmarkBoardRequestDto bookmarkBoardRequestDto,
                                                 BindingResult result){
        log.info("[[ RestController - bookmarkCancel ]]");

        if(result.hasErrors()){
            log.info("bookmark errors => {}", result.getFieldErrors());
            return new ResponseEntity<>(result.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        //Member 엔티티
        Member member = memberService.findMemberByName(bookmarkBoardRequestDto.getMember_name());

        //Bookmark 테이블에 데이터 존재여부 체크
        Optional<Bookmark> optionalBookmark = bookmarkService.findBookmarkByBoardIdAndMemberId(bookmarkBoardRequestDto.getBoard_id(), member.getId());
        if(!optionalBookmark.isPresent()){
            return new ResponseEntity<>("해당되는 북마크 데이터가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        //Bookmark 엔티티
        Bookmark bookmark = optionalBookmark.get();
        bookmarkService.cancelBookmark(bookmark);

        return new ResponseEntity<>("canceled", HttpStatus.ACCEPTED);
    }

}
