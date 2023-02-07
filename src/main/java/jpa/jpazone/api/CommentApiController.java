package jpa.jpazone.api;

import jpa.jpazone.controller.SessionConstants;
import jpa.jpazone.domain.Member;
import jpa.jpazone.service.CommentService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentApiController {

    public final CommentService commentService;

    @PostMapping("/api/recomment/post")
    public ResponseEntity<Object> recomment(@RequestParam("recomment")String recomment,
                                    @RequestParam("parentComment_id")Long parentComment_id,
                                    @RequestParam("board_id")Long board_id,
                                    @SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member member
                            ){
        log.info("[[ RestController - recomment ]]");

        Long recomment_id = commentService.saveRecomment(recomment, parentComment_id, board_id, member);
        log.info("recomment_id => {}", recomment_id);

        return new ResponseEntity<>(new RecommentDto(recomment_id), HttpStatus.OK);
    }

    @Data
    private class RecommentDto {
        private Long recomment_id;
        public RecommentDto(Long recomment_id) {
            this.recomment_id = recomment_id;
        }
    }

}
