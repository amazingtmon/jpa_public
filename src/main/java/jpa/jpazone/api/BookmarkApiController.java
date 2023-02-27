package jpa.jpazone.api;

import jpa.jpazone.api.dto.BookmarkBoardRequestDto;
import jpa.jpazone.controller.SessionConstants;
import jpa.jpazone.controller.form.BookMarkedBoardDto;
import jpa.jpazone.domain.*;
import jpa.jpazone.service.BoardService;
import jpa.jpazone.service.BookmarkService;
import jpa.jpazone.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookmarkApiController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final BookmarkService bookmarkService;

    /**
     * 북마크 생성 (게시글)
     * @param bookmarkBoardRequestDto
     * @param result
     * @return
     */
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
            //Bookmark 테이블에 data 가 존재하면서, isBookmarked 값이 true 일 경우
            if(bookmark.isBookmarked()){
                return new ResponseEntity<>("이미 북마크가 완료된 게시물입니다.", HttpStatus.BAD_REQUEST);
            }

            //다시 북마크를 하는 경우 isBookmarked 값을 true 로 update
            bookmarkService.updateBookmark(bookmark);
            return new ResponseEntity<>("update", HttpStatus.ACCEPTED);
        }

        //처음 북마크 하는 경우, Bookmark 엔티티 생성
        bookmarkService.createBookmark(board, member);

        return new ResponseEntity<>("ok", HttpStatus.CREATED);
    }

    /**
     * 북마크 취소 (게시글)
     * @param bookmarkBoardRequestDto
     * @param result
     * @return
     */
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

        return new ResponseEntity<>("canceled", HttpStatus.OK);
    }

    /**
     * 북마크 취소 게시물 리스트
     * @param loginMember
     * @return
     */
    @GetMapping("/api/bookmark/board-canceled")
    public ResponseEntity<Object> canceledBookmarks(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member loginMember){
        log.info("[[ RestController - canceledBookmarks ]]");

        //Member 엔티티
        Member member = memberService.findMemberById(loginMember.getId());
        //Bookmark 한 Board list
        List<Bookmark> boardList = bookmarkService.findAllByMemberAndItem(member.getId(), BookMarkItem.BOARD);
        // 찾아온 Board 리스트 중 BoardStatus == EXIST 인 것과 isBookmarked == false 인 것만 필터링
        List<Bookmark> bookMarkedBoards = boardList.stream().filter(bm -> bm.getBoard().getStatus() != BoardStatus.DELETED)
                .filter( bookmark -> !bookmark.isBookmarked())
                .collect(Collectors.toList());
        //Bookmark 엔티티를 BookMarkedBoardDto 로 변경
        List<BookMarkedBoardDto> boardDtos = bookMarkedBoards.stream().map(BookMarkedBoardDto::new).collect(Collectors.toList());

        return new ResponseEntity<>(new Result(boardDtos.size(), boardDtos), HttpStatus.OK);
    }

    /**
     * 북마크 데이터 영구 삭제
     * @param bookmarkBoardRequestDto
     * @param loginMember
     * @return
     */
    @DeleteMapping("/api/bookmark/board")
    public ResponseEntity<Object> deleteBookmarkEver(@RequestBody BookmarkBoardRequestDto bookmarkBoardRequestDto,
                                                     @SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member loginMember){
        log.info("[[ RestController - deleteBookmarkEver ]]");

        //Bookmark 테이블에 데이터 존재여부 체크
        Optional<Bookmark> optionalBookmark = bookmarkService.findBookmarkByBoardIdAndMemberId(bookmarkBoardRequestDto.getBoard_id(), loginMember.getId());
        if(!optionalBookmark.isPresent()){
            return new ResponseEntity<>("해당되는 북마크 데이터가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        //Bookmark 엔티티
        Bookmark bookmark = optionalBookmark.get();
        bookmarkService.deleteBookmarkEver(bookmark);

        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T bookmark_data;
    }
}
