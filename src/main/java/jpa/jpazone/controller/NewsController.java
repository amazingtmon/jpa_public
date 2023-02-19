package jpa.jpazone.controller;

import jpa.jpazone.domain.Member;
import jpa.jpazone.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;


@Slf4j
@Controller
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/news")
    public String news(Model model,
                       @SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member loginMember){
        log.info("[[ news ]]");

        model.addAttribute("session_member", loginMember);

        return "news/newsPage";
    }


}
