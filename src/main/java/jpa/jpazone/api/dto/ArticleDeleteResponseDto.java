package jpa.jpazone.api.dto;

import jpa.jpazone.domain.News;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ArticleDeleteResponseDto {

    private String article_title;

    public ArticleDeleteResponseDto(News news) {
        this.article_title = news.getArticle_title();
    }
}
