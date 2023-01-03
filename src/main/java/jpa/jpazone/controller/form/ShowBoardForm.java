package jpa.jpazone.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * 게시글 수정 및 댓글 입력시 사용되는 DTO form
 */
@Getter @Setter
public class ShowBoardForm {

    private Long board_id;

    @NotEmpty(message = "제목은 필수 입니다.")
    private String title; //제목

    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name; //작성자

    @NotEmpty(message = "내용을 입력해주세요.")
    private String content; //내용

    @NotBlank
    private String comment_content;

    public static ShowBoardForm setBoardInfo(Long boardId, String title, String name, String content){
        ShowBoardForm showBoardForm = new ShowBoardForm();
        showBoardForm.setBoard_id(boardId);
        showBoardForm.setTitle(title);
        showBoardForm.setName(name);
        showBoardForm.setContent(content);

        return showBoardForm;
    }
}
