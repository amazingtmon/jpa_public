package jpa.jpazone.api.dto;

import jpa.jpazone.domain.News;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ArticleListResponseDto {

    private Long id;
    private String article_title;
    private String article_url;
    private LocalDateTime article_regiTime;
    private LocalDateTime article_deleteTime;
    private String article_publishedAt;
    private String news_page_path;
    boolean isDeleted;

    public ArticleListResponseDto(News news){
        this.id = news.getId();
        this.article_title = news.getArticle_title();
        this.article_url = news.getArticle_url();
        this.article_regiTime = news.getArticle_regiTime();
        this.article_deleteTime = news.getArticle_deleteTime();
        this.article_publishedAt = news.getArticle_publishedAt();
        this.news_page_path = news.getNews_page_path();
        this.isDeleted = news.isDeleted();
    }
}
