package jpa.jpazone.service;

import jpa.jpazone.domain.Member;
import jpa.jpazone.domain.News;
import jpa.jpazone.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsService {

    private final NewsRepository newsRepository;

    public News findNewsById(Long id){
        log.info("[[ Service - findNewsById ]]");
        return newsRepository.findNewsById(id);
    }

    @Transactional
    public Long saveArticle(String title, String url, String publishedAt, String news_page_path, Member member) {
        log.info("[[ Service - saveArticle ]]");

        // News 엔티티 생성
        News news = new News(title, url, publishedAt, news_page_path, member);
        // News 엔티티 저장
        newsRepository.saveArticle(news);

        return news.getId();
    }

    public boolean findArticleByUrl(String url){
        log.info("[[ Service - findArticleByUrl ]]");

        List<News> article = newsRepository.findArticleByUrl(url);
        Optional<News> findArticle = article.stream().filter(a -> a.getArticle_url().equals(url)).findAny();
        return findArticle.isPresent();
    }

    public List<News> findAllArticles(Long id) {
        log.info("[[ Service - findAllArticles ]]");
        return newsRepository.findAllArticles(id);
    }

    public List<News> findArticlesByPagePath(String selected_option) {
        log.info("[[ Service - findArticlesByPagePath ]]");
        return newsRepository.findArticlesByPagePath(selected_option);
    }
}
