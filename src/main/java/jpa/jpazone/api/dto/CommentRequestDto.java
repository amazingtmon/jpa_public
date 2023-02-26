package jpa.jpazone.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentRequestDto {

    private Long comment_id;
    private String comment_content;
}
