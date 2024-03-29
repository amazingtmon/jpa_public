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

    public boolean findArticleByUrlAndMember(String url, Long user_id){
        log.info("[[ Service - findArticleByUrlAndMember ]]");

        List<News> article = newsRepository.findArticleByUrlAndMember(url, user_id);
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

    @Transactional
    public News deleteArticle(Long id) {
        log.info("[[ Service - deleteArticle ]]");

        //News 엔티티
        News news = newsRepository.findNewsById(id);
        // isDeleted 값을 true 로 변경
        news.delete();

        return news;
    }

    public List<News> findAllDeletedArticles(Long member_id) {
        log.info("[[ Service - findAllDeletedArticles ]]");
        return newsRepository.findAllDeletedArticles(member_id);
    }

    @Transactional
    public void deleteArticleEver(Long id) {
        log.info("[[ Service - deleteArticleEver ]]");

        //News 엔티티
        News news = newsRepository.findNewsById(id);

        newsRepository.deleteArticleEver(news);
    }

    @Transactional
    public News updateArticleState(Long id) {
        log.info("[[ Service - updateArticleState ]]");

        //News 엔티티
        News news = newsRepository.findNewsById(id);
        //isDeleted 값을 false 로 변경
        news.changeDeletedState();

        return news;
    }
}
