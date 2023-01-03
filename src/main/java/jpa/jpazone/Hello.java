package jpa.jpazone;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class Hello {

    @GetMapping("/hello")
    public String hello(Model model){
        model.addAttribute("hello", "Hello SpringBoot");
        log.info("{}", model.getAttribute("hello"));
        log.info("end");
        return "hello";
    }

}
