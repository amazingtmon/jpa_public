package jpa.jpazone.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NewsDto {

    private String title;
    private String description;

    public NewsDto(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
