package jpa.jpazone.mybatis.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MybatisMemberDto {

    private Long user_id;
    private String name;
    private String password;
    private Integer reported_count;
    private Boolean isBanned;
    private LocalDateTime ban_end_time;
}
