package jpa.jpazone.api;

import jpa.jpazone.api.dto.ArticleListResponseDto;
import jpa.jpazone.api.dto.ArticleRequestDto;
import jpa.jpazone.controller.SessionConstants;
import jpa.jpazone.domain.Member;
import jpa.jpazone.domain.News;
import jpa.jpazone.service.MemberService;
import jpa.jpazone.service.NewsService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/api/article-path")
    public ResponseEntity<Object> articles(@RequestParam("selected_option")String selected_option,
                                           @SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false)Member loginMember){
        log.info("[[ RestController - articles ]]");

        //selectBox 에서 선택한 option value 에 따라 가져오는 기사 리스트
        List<News> articlesList = newsService.findArticlesByPagePath(selected_option);
        //기사 리스트에서 isDeleted == false 인 것만 필터링
        List<News> articles = articlesList.stream().filter(a -> !a.isDeleted()).collect(Collectors.toList());
        // ResponseDto 로 변환
        List<ArticleListResponseDto> articleListDto = articles.stream().map(ArticleListResponseDto::new).collect(Collectors.toList());

        return new ResponseEntity<>(new Result(articleListDto.size(), articleListDto), HttpStatus.OK);
    }
    
    @PutMapping("/api/article-put")
    public ResponseEntity<Object> deleteArticle(@RequestBody ArticleRequestDto articleRequestDto,
                                                @SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false)Member loginMember){
        log.info("[[ RestController - deleteArticle ]]");
        log.info("param check => {}, {}", articleRequestDto.getId(), articleRequestDto.getId().getClass());

        return new ResponseEntity<>("ok", HttpStatus.ACCEPTED);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }
}
