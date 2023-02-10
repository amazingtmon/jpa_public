package jpa.jpazone.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice("jpa.jpazone.controller")
public class CommonExceptionAdvice {
    /**
     * Exception 관련 page
     * @param model
     * @param e
     * @return view error page
     */
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Model model, Exception e){
        log.info("Controller exception message = {}", e.getMessage());
        model.addAttribute("exception", e);

        return "error/exception";
    }
}
