package jpa.jpazone.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class BookmarkBoardRequestDto {

    @NotNull(message = "bookmark_id 의 값이 null 일 수 없습니다.")
    private Long bookmark_id;
    @NotNull(message = "board_id 의 값이 null 일 수 없습니다.")
    private Long board_id;
    @NotNull(message = "member_name 의 값이 null 일 수 없습니다.")
    private String member_name;
}
