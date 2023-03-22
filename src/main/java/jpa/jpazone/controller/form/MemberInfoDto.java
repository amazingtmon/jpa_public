package jpa.jpazone.controller.form;

import jpa.jpazone.domain.enumpackage.RoleGroup;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter @Setter
public class MemberInfoDto {

    private String name;
    private RoleGroup role;
    private Integer reported_count;
    private Boolean isBanned;
    private LocalDateTime ban_end_time;

    public MemberInfoDto(String name, RoleGroup role, Integer reported_count, Boolean isBanned, LocalDateTime ban_end_time) {
        this.name = name;
        this.role = role;
        this.reported_count = reported_count;
        this.isBanned = isBanned;
        this.ban_end_time = ban_end_time;
    }
}
