package jpa.jpazone.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * 게시글 새로 작성할때 사용되는 DTO form
 */
@Getter @Setter
public class BoardForm {

    private Long board_id;

    @NotEmpty(message = "제목은 필수 입니다.")
    private String title; //제목

    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name; //작성자

    @NotEmpty(message = "내용을 입력해주세요.")
    private String content; //내용

    public static BoardForm setBoardInfo(Long boardId, String title, String name, String content){
        BoardForm boardForm = new BoardForm();
        boardForm.setBoard_id(boardId);
        boardForm.setTitle(title);
        boardForm.setName(name);
        boardForm.setContent(content);

        return boardForm;
    }

}
