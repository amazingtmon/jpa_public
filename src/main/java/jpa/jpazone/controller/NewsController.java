package jpa.jpazone.controller;

import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class NewsController {

    @GetMapping("/news")
    public String news(Model model){
        log.info("[[ news ]]");
        NewsApiClient newsApiClient = new NewsApiClient("4d855597f2e0410fae0c1a86e5ec4e7a");
        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .q("weather")
                        .language("en")
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        System.out.println(response.getArticles().get(0).getTitle());
                        response.getArticles().forEach(article -> {
                            log.info("response.getArticles => {}, {}", article.getTitle(), article.getUrl());
                        });
                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println(throwable.getMessage());
                    }
                }
        );
        List<String> articles = new ArrayList<>();
        model.addAttribute("articles", "Hello News!!");

        return "news/newsPage";
    }
}
