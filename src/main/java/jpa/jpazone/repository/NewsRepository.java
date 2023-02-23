package jpa.jpazone.repository;

import jpa.jpazone.domain.Member;
import jpa.jpazone.domain.News;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class NewsRepository {

    private final EntityManager em;

    public News findNewsById(Long id){
        log.info("[[ Repo - findNewsById ]]");

        return em.find(News.class, id);
    }

    public void saveArticle(News news) {
        log.info("[[ Repo - saveArticle ]]");

        em.persist(news);
    }

    public List<News> findArticleByUrl(String url) {
        log.info("[[ Repo - findArticleByUrl ]]");
        return em.createQuery("select n from News n where n.article_url = :url", News.class)
                .setParameter("url", url)
                .getResultList();
    }

    public List<News> findAllArticles(Long id) {
        log.info("[[ Repo - findAllArticles ]]");
        return em.createQuery("select n from News n where n.member.id = :id", News.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<News> findArticlesByPagePath(String selected_option) {
        log.info("[[ Repo - findArticlesByPagePath ]]");

        return em.createQuery("select n from News n where n.news_page_path = :selected_option",  News.class)
                .setParameter("selected_option", selected_option)
                .getResultList();
    }

    public List<News> findAllDeletedArticles(Long member_id) {
        log.info("[[ Repo - findAllDeletedArticles ]]");

        return em.createQuery("select n from News n where n.isDeleted = true and n.member.id = :member_id", News.class)
                .setParameter("member_id", member_id)
                .getResultList();
    }

    public void deleteArticleEver(News news) {
        log.info("[[ Repo - deleteArticleEver ]]");

        em.remove(news);
    }
}
