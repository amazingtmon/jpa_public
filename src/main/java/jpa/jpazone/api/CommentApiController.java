package jpa.jpazone.api;

import jpa.jpazone.api.dto.CommentRequestDto;
import jpa.jpazone.api.dto.ReCommentRequestDto;
import jpa.jpazone.controller.SessionConstants;
import jpa.jpazone.controller.form.CommentForm;
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

    @PostMapping("/api/comment/post")
    public ResponseEntity<String> comment(@RequestBody CommentForm commentForm,
                                          @SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member member
    ){
        log.info("[[ RestController -  comment ]]");

        commentService.saveComment(commentForm.getComment_content(), commentForm.getBoard_id(), member);

        return new ResponseEntity<>("ok", HttpStatus.CREATED);
    }

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

        return new ResponseEntity<>(new RecommentDto(recomment_id), HttpStatus.CREATED);
    }

    @Data
    private class RecommentDto {
        private Long recomment_id;
        public RecommentDto(Long recomment_id) {
            this.recomment_id = recomment_id;
        }
    }

    @PutMapping("/api/comment")
    public ResponseEntity<Object> updateComment(@RequestBody CommentRequestDto commentRequestDto){
        log.info("[[ RestController - updateComment ]]");

        commentService.updateComment(commentRequestDto.getComment_id(), commentRequestDto.getComment_content());

        return new ResponseEntity<>("updated", HttpStatus.CREATED);
    }

    @DeleteMapping("/api/comment")
    public ResponseEntity<Object> deleteComment(@RequestBody CommentRequestDto commentRequestDto){
        log.info("[[ RestController - deleteComment ]]");

        commentService.deleteComment(commentRequestDto.getComment_id());

        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }

}
