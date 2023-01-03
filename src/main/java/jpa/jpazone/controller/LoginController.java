package jpa.jpazone.controller;

import jpa.jpazone.controller.form.LoginForm;
import jpa.jpazone.domain.Member;
import jpa.jpazone.service.LoginService;
import jpa.jpazone.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final LoginService loginService;

    @GetMapping("/login")
    public String login(@ModelAttribute LoginForm loginForm){
        log.info("[[ login try ]]");
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm")LoginForm loginForm, BindingResult result
                        ,HttpServletRequest request){
        log.info("[[ loginForm submit ]]");
        if(result.hasErrors()){
            log.info(String.valueOf(result.getFieldError()));
            return "login/loginForm";
        }

        Member loginMember = loginService.findByloginId(loginForm.getName(), loginForm.getPassword());
        if(loginMember == null){
            result.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        HttpSession session = request.getSession();
        log.info("login session before setAttribute = {}", session);
        session.setAttribute(SessionConstants.LOGIN_MEMBER, loginMember);
        log.info("login session = {}", session.getId());

        //세션 타임아웃시간 설정
        session.setMaxInactiveInterval(1800);

        //로그인 성공시 home 화면으로
        return "redirect:/session";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();// 세션 날림
        }

        return "redirect:/loginForm";
    }

    @PostMapping("/account/login")
    public String loginCheck (@RequestParam String username, @RequestParam String password){
        log.info("[[ Try Login ]]");
        log.info("username = {}, password = {}", username, password);

        Boolean result = memberService.findMemberByNamePw(username, password);
        log.info("Boolean result = {}", result);

        if(result){
            return "redirect:/home";
        }

        return "redirect:/loginPage";
    }

    @PostMapping("/account/login2")
    public String loginCheck2 (@RequestParam String username, @RequestParam String password){
        log.info("[[ Try Login 2 ]]");
        log.info("username = {}, password = {}", username, password);

        Boolean result = memberService.findMemberByNamePw2(username, password);
        log.info("Boolean result = {}", result);

        //로그인 성공시 홈 화면으로 보낸다
        if(result){
            return "redirect:/home";
        }

        return "redirect:/loginPage";
    }
}
