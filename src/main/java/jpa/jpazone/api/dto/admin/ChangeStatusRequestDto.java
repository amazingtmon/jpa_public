package jpa.jpazone.api.dto.admin;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChangeStatusRequestDto {

    private Long report_id;
    private String report_handle_status;
}
