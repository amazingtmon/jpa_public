package jpa.jpazone.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentApiController {

    @PostMapping("/api/recomment/post")
    public String recomment(@RequestParam("recomment")String recomment,
                            @RequestParam("comment_id")Long comment_id,
                            @RequestParam("board_id")Long board_id){
        log.info("[[ RestController - recomment ]]");
        log.info("param 1st, 2nd => {} {}", recomment, comment_id);
        log.info("param 3rd => {}", board_id);

        return "ok";
    }
}
