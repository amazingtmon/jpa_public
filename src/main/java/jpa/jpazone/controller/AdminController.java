package jpa.jpazone.controller;

import jpa.jpazone.service.*;
import jpa.jpazone.service.dto.ContentsCountDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/adminPage")
    public String adminPage(Model model){
        log.info("[[ adminPage ]]");

        List<ContentsCountDto> contentsCount = adminService.findAllContentsCount();

        model.addAttribute("contentsCount", contentsCount);

        return "admin/adminPage";
    }
}
