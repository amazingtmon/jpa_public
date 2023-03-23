package jpa.jpazone.controller;

import jpa.jpazone.controller.form.LoginForm;
import jpa.jpazone.domain.Member;
import jpa.jpazone.service.LoginService;
import jpa.jpazone.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final LoginService loginService;

    @GetMapping("/login")
    public String login(@ModelAttribute LoginForm loginForm, HttpServletRequest request){
        log.info("[[ login try ]]");

        HttpSession session = request.getSession(false);
        if (session != null) {
            log.info("session name, last access time => {}, {}", session.getId(), new Date(session.getLastAccessedTime()));
            session.invalidate();// 세션 날림
        }

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

        /* Member 의 isBanned 값이 true 인 경우 */
        if(loginMember.getIsBanned()){
            loginService.validateBanMember(loginMember);
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConstants.LOGIN_MEMBER, loginMember);
        log.info("login session = {}, {}", session.getId(), new Date(session.getCreationTime()));

        //세션 타임아웃시간 설정
        session.setMaxInactiveInterval(1800);

        //로그인 성공시 home 화면으로
        return "redirect:/mainHome";
    }

    @PostMapping("/logout")
    public String logout(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member member,
                         HttpServletRequest request) {
        log.info("user = "+member.getName()+" logout");
        HttpSession session = request.getSession(false);
        if (session != null) {
            log.info("logout session out => {}", new Date(session.getLastAccessedTime()));
            session.invalidate();// 세션 날림
        }

        return "redirect:/login";
    }

}
