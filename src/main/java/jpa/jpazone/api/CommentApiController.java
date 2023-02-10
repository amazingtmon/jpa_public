package jpa.jpazone.api;

import jpa.jpazone.api.dto.ReCommentRequestDto;
import jpa.jpazone.controller.SessionConstants;
import jpa.jpazone.domain.Member;
import jpa.jpazone.service.CommentService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentApiController {

    public final CommentService commentService;

    @PostMapping("/api/recomment/post")
    public ResponseEntity<Object> recomment(@RequestBody @Valid ReCommentRequestDto reCommentRequestDto,
                                            BindingResult result,
                                            @SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member member
    ){
        log.info("[[ RestController - recomment ]]");

        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        Long recomment_id = commentService.saveRecomment(reCommentRequestDto.getRecomment(),
                                                        reCommentRequestDto.getParentComment_id(),
                                                        reCommentRequestDto.getBoard_id(),
                                                        member);
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

    @PutMapping("/api/comment")
    public String updateComment(@RequestParam("comment_id")Long comment_id,
                                @RequestParam("comment_content")String comment_content){
        log.info("[[ RestController - updateComment ]]");

        commentService.updateComment(comment_id, comment_content);

        return "ok";
    }

    @DeleteMapping("/api/comment")
    public String deleteComment(@RequestParam("comment_id")Long comment_id){
        log.info("[[ RestController - deleteComment ]]");

        commentService.deleteComment(comment_id);

        return "ok";
    }

}
