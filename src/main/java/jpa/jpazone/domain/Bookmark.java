package jpa.jpazone.domain;

import jpa.jpazone.domain.enumpackage.BookMarkItem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark {

    @Id @GeneratedValue
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private LocalDateTime bookmark_time; //북마크 등록 시간
    private LocalDateTime bookmark_cancel_time; //북마크 취소 시간
    boolean isBookmarked; // 북마크 상태 True : 북마크, False : 북마크 해제
    private String bookmark_item_title;

    @Enumerated(EnumType.STRING)
    private BookMarkItem bookmarkItem;

    //==연관관계 메서드==//
    public void setMember(Member member){
        this.member = member;
        member.getBookmarks().add(this);
    }

    public void setBoard(Board board){
        this.board = board;
    }

    public Bookmark(Board board, Member member) {
        this.setMember(member);
        this.setBoard(board);
        this.bookmark_time = LocalDateTime.now();
        this.isBookmarked = true;
        this.bookmark_item_title = board.getTitle();
        this.bookmarkItem = BookMarkItem.BOARD;
    }

    public void change() {
        this.isBookmarked = true;
        this.bookmark_time = LocalDateTime.now();
    }

    public void cancel() {
        this.isBookmarked = false;
        this.bookmark_cancel_time = LocalDateTime.now();
    }
}
