package jpa.jpazone.controller;

import jpa.jpazone.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        log.info("[[ createForm ]]");
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm memberForm, BindingResult result){
        log.info("[[ joinForm from View ]]");
        log.info("memberForm name = {}", memberForm.getName());
        log.info("memberForm city = {}", memberForm.getCity());
        log.info("memberForm street = {}", memberForm.getStreet());
        log.info("memberForm zipcode = {}", memberForm.getZipcode());
        if(result.hasErrors()){
            log.info(String.valueOf(result.getFieldError()));
            return "members/createMemberForm";
        }
//        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());
//        Member member = new Member();
//        member.setAddress(address);
//        member.setName(memberForm.getName());
//
//        memberService.join(member);
        return "redirect:/home";
    }
}
