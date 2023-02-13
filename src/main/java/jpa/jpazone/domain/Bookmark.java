package jpa.jpazone.domain;

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

    private LocalDateTime bookmark_time; //북마크 등록 시간
    private LocalDateTime bookmark_cancel_time; //북마크 취소 시간
    boolean isBookMarked; // 북마크 상태 True : 북마크, False : 북마크 해제
    private Long bookmark_item_id;
    private String bookmark_item_title;

    @Enumerated(EnumType.STRING)
    private BookMarkItem bookMarkItem;

    //==연관관계 메서드==//
    public void setMember(Member member){
        this.member = member;
        member.getBookmarks().add(this);
    }

    public Bookmark(Board board, Member member) {
        this.member = member;
        this.bookmark_time = LocalDateTime.now();
        this.isBookMarked = true;
        this.bookmark_item_id = board.getId();
        this.bookmark_item_title = board.getTitle();
        this.bookMarkItem = BookMarkItem.BOARD;
    }


}
