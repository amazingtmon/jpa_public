package jpa.jpazone.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotBlank
    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name;
    @NotBlank
    private String city;
    @NotBlank
    private String street;
    @NotBlank
    private String zipcode;
}
