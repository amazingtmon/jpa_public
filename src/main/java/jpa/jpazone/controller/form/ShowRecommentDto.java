package jpa.jpazone.controller.form;

import jpa.jpazone.domain.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ShowRecommentDto {

    private Long comment_id;
    private String name;
    private String comment_content;
    private LocalDateTime write_date; //댓글작성시간
    private LocalDateTime update_date; //댓글작성시간
    private Long parentComment_id; //부모 comment id
    private int deep; //댓글과 대댓글 계층을 표현하기 위한 필드값
    private boolean isRemoved; //댓글 삭제 여부

    public ShowRecommentDto(Comment child){
        this.comment_id = child.getId();
        this.name = child.getWriter();
        this.comment_content = child.getComment_content();
        this.write_date = child.getWrite_date();
        this.update_date = child.getUpdate_date();
        this.parentComment_id = child.getParent().getId();
        this.deep = child.getDeep();
        this.isRemoved = child.isRemoved();
    }
}
