package jpa.jpazone.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AdminRepository {

    private final EntityManager em;

    public List<Object[]> findAllContentsCount(){

        String sql1 = "select\n" +
                " (select count(*) from member ) as memberCount,\n" +
                " (select count(*) from board ) as boardsCount,\n" +
                " (select count(*) from comment ) as commentsCount,\n" +
                " (select count(*) from bookmark ) as bookmarksCount,\n" +
                " (select count(*) from news ) as newsCount,\n" +
                " (select count(*) from report ) as reportsCount,\n" +
                "from dual";
        List<Object[]> resultList = em.createNativeQuery(sql1).getResultList();
        return resultList;
    }

    public List<Object[]> findMemberContentUseStatistics(){
        log.info("[[ Repo - findMemberContentUseStatistics ]]");

        String sql1 = "select \n" +
                " m.user_id, m.name, \n" +
                " (select count(*) from board b where b.user_id = m.user_id) as board_count,\n" +
                " (select count(*) from comment c where c.user_id = m.user_id) as comment_count,\n" +
                " (select count(*) from bookmark bm where bm.user_id = m.user_id) as bookmark_count,\n" +
                " (select count(*) from news n where n.user_id = m.user_id) as news_count,\n" +
                " (select count(*) from report r where r.user_id = m.user_id) as report_count,\n" +
                "from Member m";
        List<Object[]> resultList = em.createNativeQuery(sql1).getResultList();
        return resultList;
    }

}
