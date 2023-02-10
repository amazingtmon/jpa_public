package jpa.jpazone.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice("jpa.jpazone.api")
public class RestControllerExceptionAdvice {
    /**
     * RestController에서 발생하는 에러 처리
     * @param e
     * @return exception Message, HttpStatus code
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> RestControllerExceptionHandler(Exception e){
        log.info("RestController exception message = {}", e.getMessage());
        String error = e.getMessage();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
