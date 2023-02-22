package jpa.jpazone.service;

import jpa.jpazone.domain.Member;
import jpa.jpazone.domain.News;
import jpa.jpazone.repository.NewsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class NewsServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    NewsRepository newsRepository;

    @Test
    public void 기사저장테스트() throws Exception {
        // given
        String article_title = "사설 ICBM 이어 초대형방사포로 청주·군산 겨냥한 북  한겨레";
        String article_url = "https://www.hani.co.kr/arti/opinion/editorial/1080481.html";
        String publishedAt = "2023-01-27 21:30:00";
        String news_page_path = "everything";
        Long user_id = 1L;

        // when
        //Member 엔티티
        Member member = memberService.findMemberById(user_id);
        //News 엔티티 생성
        News news = new News(article_title, article_url, publishedAt, news_page_path, member);
        //News 엔티티 save
        newsRepository.saveArticle(news);
        //News 엔티티 find
        News findNews = newsRepository.findNewsById(news.getId());
        System.out.println("findNews title => "+findNews.getArticle_title());
        System.out.println("findNews id => "+findNews.getId());
        // then
    }

    @Test
    public void 기사url로찾아보기() throws Exception {
        // given
        String article_url = "https://www.mbs.jp/news/kansainews/20230220/GE00048428.shtml";

        // when
        List<News> article = newsRepository.findArticleByUrl(article_url);
        System.out.println("size => "+article.size());
        Optional<News> findArticle = article.stream().filter(a -> a.getArticle_url().equals(article_url)).findAny();

        // then
        assertEquals(true, findArticle.isPresent());
    }


    @Test
    public void 유저가저장한모든기사들가져오기() throws Exception {
        // given
        Long user_id = 1L; //member dummyData

        // when
        List<News> articlesList = newsRepository.findAllArticles(user_id);
        System.out.println("allArticles size => "+ articlesList.size());
        List<News> allArticles = articlesList.stream().filter(a -> !a.isDeleted()).collect(Collectors.toList());
        allArticles.stream().forEach( a -> {
            System.out.println("allArticles id => "+a.getId()+", "+a.getArticle_title());
        });

        // then
    }
    
    @Test
    public void 선택한옵션에맞는기사리스트() throws Exception {
        // given
        String top = "topHeadline";
        String search = "everything";

        // when
        //get article List
        List<News> articlesByPagePath = newsRepository.findArticlesByPagePath(top);
        //filter that articles are not deleted(== false)
        List<News> articles = articlesByPagePath.stream().filter(a -> !a.isDeleted()).collect(Collectors.toList());
        articles.stream().forEach(a -> {
            System.out.println("article title => "+a.getArticle_title());
        });

        // then

    }
}