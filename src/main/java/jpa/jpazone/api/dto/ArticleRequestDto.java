package jpa.jpazone.api.dto;

import lombok.*;

@Getter @Setter
public class ArticleRequestDto {

    private Long id;
    private String title;
    private String url;
    private String publishedAt;
    private String news_page_path;

}
