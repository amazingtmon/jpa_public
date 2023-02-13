package jpa.jpazone.repository;

import jpa.jpazone.domain.Bookmark;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BookmarkRepository {

    private final EntityManager em;

    public void save(Bookmark bookMark) {
        em.persist(bookMark);
    }

    public List<Bookmark> findBookmarkByBoardIdAndMemberId(Long board_id, Long user_id){
        log.info("[[ Repo - findBookmarkByBoardIdAndMemberId ]]");
        String str = "select * from bookmark where bookmark_item_id ="+board_id+" and user_id ="+user_id;
/*        return em.createQuery("select bm from Bookmark bm where bm.bookmark_item_id = :board_id " +
                        "and bm.Member.id = :user_id", Bookmark.class)
                .setParameter("board_id", board_id)
                .setParameter("user_id", user_id)
                .getResultList();*/
        return em.createNativeQuery(str, Bookmark.class).getResultList();
    }

}
