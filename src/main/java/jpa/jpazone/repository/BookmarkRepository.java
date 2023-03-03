package jpa.jpazone.repository;

import jpa.jpazone.domain.enumpackage.BookMarkItem;
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

        return em.createQuery("select bm from Bookmark bm where bm.board.id = :board_id " +
                                    "and bm.member.id = :user_id", Bookmark.class)
                .setParameter("board_id", board_id)
                .setParameter("user_id", user_id)
                .getResultList();
    }

    public List<Bookmark> findAllByMemberAndItem(Long user_id, BookMarkItem bmi_item) {
        log.info("[[ Repo - findAllByMemberAndItem ]]");
        List resultList = em.createQuery("select bm from Bookmark bm " +
                        "join fetch bm.member m " +
                        "join fetch bm.board b " +
                        "where bm.member.id = : user_id " +
                        "and bm.bookmarkItem = :bmi_item")
                .setParameter("user_id", user_id)
                .setParameter("bmi_item", bmi_item)
                .getResultList();

        return resultList;
    }

    public void deleteBookmarkEver(Bookmark bookmark) {
        log.info("[[ Repo - deleteBookmarkEver ]]");

        em.remove(bookmark);
    }
}
