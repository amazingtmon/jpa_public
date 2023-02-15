package jpa.jpazone.controller.form;

import jpa.jpazone.domain.Bookmark;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class BookMarkedBoardDto {

    private Long id; //board_id
    private String title; //board_title
    private LocalDateTime bookmark_time; //북마크 등록 시간
    private LocalDateTime bookmark_cancel_time; //북마크 취소 시간
    boolean isBookMarked; // 북마크 상태 True : 북마크, False : 북마크 해제

    public BookMarkedBoardDto(Bookmark bookmark) {
        this.id = bookmark.getBookmark_item_id();
        this.title = bookmark.getBookmark_item_title();
        this.bookmark_time = bookmark.getBookmark_time();
        this.bookmark_cancel_time = bookmark.getBookmark_cancel_time();
        this.isBookMarked = bookmark.isBookMarked();
    }
}
