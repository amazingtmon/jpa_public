package jpa.jpazone.api;

import jpa.jpazone.api.dto.ArticleRequestDto;
import jpa.jpazone.controller.SessionConstants;
import jpa.jpazone.domain.Member;
import jpa.jpazone.service.MemberService;
import jpa.jpazone.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NewsApiController {

    private final NewsService newsService;
    private final MemberService memberService;

    @PostMapping("/api/article")
    public ResponseEntity<Object> saveArticle(@RequestBody ArticleRequestDto articleRequestDto,
                                              @SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false)Member loginMember) {
        log.info("[[ RestController - saveArticle ]]");
        log.info("request title, url => {}, {}", articleRequestDto.getTitle(), articleRequestDto.getUrl());

        //중복기사 체크
        if(newsService.findArticleByUrl(articleRequestDto.getUrl())){
            return new ResponseEntity<>("dupe article", HttpStatus.BAD_REQUEST);
        }

        //Member 엔티티
        Member member = memberService.findMemberById(loginMember.getId());
        //News 엔티티 생성 및 저장
        newsService.saveArticle(articleRequestDto.getTitle(), articleRequestDto.getUrl(),
                                articleRequestDto.getPublishedAt(), articleRequestDto.getNews_page_path(), member);

        return new ResponseEntity<>("ok", HttpStatus.ACCEPTED);
    }
}
