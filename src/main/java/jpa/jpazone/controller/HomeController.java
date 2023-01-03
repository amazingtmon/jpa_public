package jpa.jpazone.controller;

import jpa.jpazone.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/session")
    public String home(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false)Member loginMember
            , Model model){
        log.info("Home loginMember = {}", loginMember);

        if(loginMember == null){
            return "login/loginForm";
        }

        model.addAttribute("member", loginMember);

        return "home";
    }

    @GetMapping("/home")
    public String mainHome(){
        log.info("[[ Home ]]");

        return "home";
    }
}
