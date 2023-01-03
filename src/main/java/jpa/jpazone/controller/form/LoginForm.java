package jpa.jpazone.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class LoginForm {

    @NotBlank
    private String name;

    @NotBlank
    private String password;


}
