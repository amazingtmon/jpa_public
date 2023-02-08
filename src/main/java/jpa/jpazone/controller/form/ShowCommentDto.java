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
    private Long parentComment_id; //부모 comment id
    private int deep; //댓글과 대댓글 계층을 표현하기 위한 필드값
    private boolean isRemoved; //댓글 삭제 여부
    private List<ShowRecommentDto> child;

    public ShowCommentDto(Comment comment){
        this.comment_id = comment.getId();
        this.name = comment.getWriter();
        this.comment_content = comment.getComment_content();
        this.write_date = comment.getWrite_date();
        this.update_date = comment.getUpdate_date();
        /**
         *  대댓글이 아닌 처음 등록된 댓글의 경우 Comment parent 객체의 값이 null 로 되어있어
         *  게시판을 클릭하여 확인하려 할 때, NPE가 발생했다.
         *  ========================================================
         *  Optional을 이용하여 parent가 null 일 경우
         *  view로 전달하는 parentComment_id 는 0L 으로 세팅하여 진행
         */
        Optional<Comment> parent = Optional.ofNullable(comment.getParent());
        if(parent.isPresent()){
            this.parentComment_id = parent.get().getId();
        } else {
            this.parentComment_id = 0L;
        }
        this.deep = comment.getDeep();
        this.isRemoved = comment.isRemoved();
        this.child = comment.getChildList().stream()
                .map(childComment -> new ShowRecommentDto(childComment))
                .collect(Collectors.toList());
    }

}
