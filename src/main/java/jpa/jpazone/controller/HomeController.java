package jpa.jpazone.controller;

import jpa.jpazone.controller.form.MemberInfoDto;
import jpa.jpazone.domain.Member;
import jpa.jpazone.domain.MemberRole;
import jpa.jpazone.domain.enumpackage.RoleGroup;
import jpa.jpazone.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

    @GetMapping("/mainHome")
    public String home(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false)Member loginMember
            , Model model){
        log.info("Home loginMember = {}", loginMember);

        if(loginMember == null){
            return "login/loginForm";
        }
        Member member = memberService.findMemberById(loginMember.getId());

        MemberInfoDto memberInfoDto = memberService.transformToMemberInfoDto(member);

        model.addAttribute("member", memberInfoDto);

        return "home";
    }

}
