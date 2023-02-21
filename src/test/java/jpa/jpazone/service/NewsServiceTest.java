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
}