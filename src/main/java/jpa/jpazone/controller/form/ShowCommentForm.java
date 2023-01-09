package jpa.jpazone.controller.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
public class ShowCommentForm {

    private Long comment_id;
    private String name;
    private String comment_content;
    private LocalDateTime write_date; //댓글작성시간
    private LocalDateTime update_date; //댓글작성시간
}
