package jpa.jpazone.repository;

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

}
