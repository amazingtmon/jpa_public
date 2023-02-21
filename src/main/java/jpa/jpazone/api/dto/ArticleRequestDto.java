package jpa.jpazone.api.dto;

import lombok.*;

@Getter @Setter
public class ArticleRequestDto {

    private String title;
    private String url;
    private String publishedAt;
    private String news_page_path;

}
