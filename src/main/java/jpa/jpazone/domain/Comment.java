package jpa.jpazone.domain;

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
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")//매핑하려는 컬럼, FK
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    private String writer;
    private String comment_content;
    private LocalDateTime write_date;
    private LocalDateTime update_date;

    //==연관관계 메서드==//
    public void setMember(Member member){
        this.member = member;
        member.getComments().add(this);
    }

    public void setBoard(Board board){
        this.board = board;
        board.getComments().add(this);
    }

    public static Comment createComment(Board board, Member member, String writer, String comment_content, LocalDateTime write_date){
        Comment comment = new Comment();
        comment.setBoard(board);
        comment.setMember(member);
        comment.setWriter(writer);
        comment.setComment_content(comment_content);
        comment.setWrite_date(write_date);
        return comment;
    }

}
