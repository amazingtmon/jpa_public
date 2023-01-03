package jpa.jpazone.controller;

import jpa.jpazone.domain.Comment;
import jpa.jpazone.domain.Member;
import jpa.jpazone.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentController {

    public final CommentService commentService;

    @PostMapping("/comment/post")
    public String comment(@ModelAttribute("comment") Comment comment,
                          Long board_id,
                          @SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member member){
        log.info("[[ comment ]]");
        log.info("get model = {}", comment.getComment_content());
        log.info("get member name, id = {} {}", member.getName(), member.getId());

        commentService.saveComment(comment, board_id, member);

        return "redirect:/board/post/"+board_id;
    }
}
