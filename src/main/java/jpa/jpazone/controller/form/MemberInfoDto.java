package jpa.jpazone.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class MemberInfoDto {

    private String name;
    private List<String> role_list;
    private Integer reported_count;
    private Boolean isBanned;
    private LocalDateTime ban_end_time;

    public MemberInfoDto(String name, List<String> role_list, Integer reported_count, Boolean isBanned, LocalDateTime ban_end_time) {
        this.name = name;
        this.role_list = role_list;
        this.reported_count = reported_count;
        this.isBanned = isBanned;
        this.ban_end_time = ban_end_time;
    }
}
