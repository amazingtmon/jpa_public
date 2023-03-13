package jpa.jpazone.controller.form;

import jpa.jpazone.domain.enumpackage.RoleGroup;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter @Setter
public class MemberInfoDto {

    private String name;
    private RoleGroup role;
    public MemberInfoDto(String name, RoleGroup member_role) {
        this.name = name;
        this.role = member_role;
    }
}
