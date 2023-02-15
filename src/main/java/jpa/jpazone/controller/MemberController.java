package jpa.jpazone.controller;

import jpa.jpazone.controller.form.MemberInfoForm;
import jpa.jpazone.controller.form.MemberNewForm;
import jpa.jpazone.domain.Member;
import jpa.jpazone.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        log.info("[[ createForm ]]");
        model.addAttribute("memberNewForm", new MemberNewForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(Model model, @Valid MemberNewForm memberNewForm, BindingResult result){
        log.info("[[ Post MemberNewForm ]]");
        log.info("MemberNewForm name = {}", memberNewForm.getName());
        if(result.hasErrors()){
            log.info(String.valueOf(result.getFieldError()));
            log.info("result = {} {}", result.getFieldErrors(), result.getGlobalErrors());
            return "members/createMemberForm";
        }
        Member member = new Member();
        //Member에 name, password 값 세팅해주기
        member.setName(memberNewForm.getName());
        member.setPassword(memberNewForm.getPassword());

        Long dupeCheck = memberService.join(member);
        if(dupeCheck == 0L){
            log.info("dupeMemberChecked => {}", dupeCheck);
            String dupeMemberExist = "동일한 ID의 사용자가 존재합니다.";
            model.addAttribute("dupeMemberExist", dupeMemberExist);
            return "members/createMemberForm";
        }

        return "redirect:/login";
    }

}
