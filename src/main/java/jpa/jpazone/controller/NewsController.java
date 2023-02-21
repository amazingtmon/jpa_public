package jpa.jpazone.controller;

import jpa.jpazone.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@Controller
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/news/topHeadline")
    public String newsTopHeadline(Model model){
        log.info("[[ news ]]");

        return "news/newsPage";
    }

    @GetMapping("/news/everything")
    public String newsEverything(Model model){
        log.info("[[ newsEverything ]]");

        return "news/newsEverything";
    }


}
