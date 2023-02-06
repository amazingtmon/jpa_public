package jpa.jpazone.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> childList = new ArrayList<>();

    private String writer;
    private String comment_content;
    private LocalDateTime write_date;
    private LocalDateTime update_date;
    private boolean isRemoved; //댓글 삭제 여부
    private int deep; //댓글과 대댓글 계층을 표현하기 위한 필드값

    //==연관관계 메서드==//
    public void setMember(Member member){
        this.member = member;
        member.getComments().add(this);
    }

    public void setBoard(Board board){
        this.board = board;
        board.getComments().add(this);
    }

//    public void setParent(Comment parent){
//        this.parent = parent;
//        parent.getChildList().add(this);
//    }

    public void addChildComment(Comment child) {
        this.childList.add(child);
        child.setParent(this);
    }

    public static Comment createComment(Board board, Member member, String writer, String comment_content, LocalDateTime write_date){
        Comment comment = new Comment();
        comment.setMember(member);
        comment.setBoard(board);
        comment.setWriter(writer);
        comment.setComment_content(comment_content);
        comment.setWrite_date(write_date);
        comment.setRemoved(false);
        comment.setDeep(0);
        return comment;
    }

}
