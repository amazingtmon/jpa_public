package jpa.jpazone.controller.form;

import jpa.jpazone.domain.Comment;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 게시글 수정 및 댓글 입력시 사용되는 DTO form
 */
@Getter @Setter
public class ShowBoardForm {

    private Long board_id;
    private String session_loginMemberName; //현재 로그인중인 멤버이름
    @NotEmpty(message = "제목은 필수 입니다.")
    private String title; //제목

    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name; //작성자

    @NotEmpty(message = "내용을 입력해주세요.")
    private String content; //내용

    private String comment_content;
    private boolean isBookmarked;
    private List<ShowCommentDto> commentDto;

    public static ShowBoardForm setBoardInfo(Long boardId, String title, String name, String content,
                                             List<Comment> boardComments, String session_loginMember, boolean bookMarked){
        ShowBoardForm showBoardForm = new ShowBoardForm();
        showBoardForm.setBoard_id(boardId);
        showBoardForm.setTitle(title);
        showBoardForm.setName(name);
        showBoardForm.setContent(content);
        showBoardForm.commentDto =
                boardComments.stream()
                        .filter(comment -> comment.getDeep() == 0)
                        .map(comment -> new ShowCommentDto(comment))
                        .collect(Collectors.toList());
        showBoardForm.setSession_loginMemberName(session_loginMember);
        showBoardForm.setBookmarked(bookMarked);

        return showBoardForm;
    }
}
