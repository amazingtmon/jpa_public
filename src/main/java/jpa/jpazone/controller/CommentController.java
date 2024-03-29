package jpa.jpazone.controller;

import jpa.jpazone.controller.form.CommentForm;
import jpa.jpazone.domain.Member;
import jpa.jpazone.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentController {

    public final CommentService commentService;

    /**
     * 댓글 등록
     * ==========================================
     * 23-03-24
     * Member 의 isBanned 유효성 검사로 인해 댓글 등록시 ajax 사용이 필요하여,
     * CommentApiController (/api/comment/post) 로 옮김
     *
     */
/*    @PostMapping("/comment/post")
    public String comment(@Valid @ModelAttribute("comment") CommentForm comment,
                          BindingResult result,
                          Long board_id,
                          @SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member member,
                          RedirectAttributes redirectAttributes){
        log.info("[[ comment ]]");

        *//*
            comment_content 내용이 없을때 발생하는 예외 NPE 처리.
            redirect 방식으로 넘어갈경우 에러메세지가 전달되지 않음.
            프런트 단에서 '내용을 입력해주세요'라는 alert 메세지를 띄워주거나 ajax 방식으로 해야할듯.
            =================================================================================
            redirectAttributes 사용하여 해결 !!
         *//*
        if(result.hasErrors()){
            log.info("comment error = {}", result.getFieldError());
            redirectAttributes.addFlashAttribute("cm_errors", result.getFieldError().getDefaultMessage());
            return "redirect:/board/post/"+board_id;
        }

        //commentService.saveComment(comment, board_id, member);

        return "redirect:/board/post/"+board_id;
    }*/
}
