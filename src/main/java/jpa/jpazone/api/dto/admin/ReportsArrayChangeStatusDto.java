package jpa.jpazone.api.dto.admin;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ReportsArrayChangeStatusDto {

    private String report_handle_status;
    private List<Long> reportIdArray;
}
