package jpa.jpazone.api.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class ReCommentRequestDto {

    @NotBlank(message = "댓글 내용은 공백일 수 없습니다.")
    private String recomment;
    @NotNull(message = "상위 댓글의 id는 필수입니다.")
    private Long parentComment_id;
    @NotNull(message = "해당 게시판의 id는 필수입니다.")
    private Long board_id;
}
