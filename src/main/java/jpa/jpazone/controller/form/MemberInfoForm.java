package jpa.jpazone.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberInfoForm {

    private String name;
    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String password;
}
