package jpa.jpazone.controller.form;

import jpa.jpazone.domain.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter @Setter
public class ShowCommentDto {

    private Long comment_id;
    private String name;
    private String comment_content;
    private LocalDateTime write_date; //댓글작성시간
    private LocalDateTime update_date; //댓글수정시간
    private int deep; //댓글과 대댓글 계층을 표현하기 위한 필드값
    private boolean isRemoved; //댓글 삭제 여부
    private List<ShowRecommentDto> child;

    public ShowCommentDto(Comment comment){
        this.comment_id = comment.getId();
        this.name = comment.getWriter();
        this.comment_content = comment.getComment_content();
        this.write_date = comment.getWrite_date();
        this.update_date = comment.getUpdate_date();
        this.deep = comment.getDeep();
        this.isRemoved = comment.isRemoved();
        this.child = comment.getChildList().stream()
                .filter( childComment -> childComment.getDeep() == 1)
                .map(childComment -> new ShowRecommentDto(childComment))
                .collect(Collectors.toList());
    }

}
