package jpa.jpazone.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReportItemRequestDto {

    private Long id;
    private String reported_mem_name;
    private String report_item;
    private String reason;
}
