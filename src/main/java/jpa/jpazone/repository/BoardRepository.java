package jpa.jpazone.repository;

import jpa.jpazone.domain.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    public void save(Board board){
        log.info("[[ Repo - board saved ]]");
        em.persist(board);
    }


    public List<Board> findAllBoards(int offset, int limit) {
        log.info("[[ Repo - findAllBoards ]]");
        String sql1 = "select rownum as rn, * from Board";
        String sql2 = "select rownum as rn, b from Board b order by b.id asc";
        return  em.createNativeQuery(sql1, Board.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public Board findBoard(Long boardId) {
        log.info("[[ Repo - findBoard ]]");

        return em.find(Board.class, boardId);
    }

    public List<Board> findBoardByKeyword(String keyword, int offset, int limit) {
        log.info("[[ Repo - findBoardByKeyword ]]");

        return em.createQuery("select b from Board b where b.title like :keyword", Board.class)
                .setParameter("keyword", "%"+keyword+"%")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }


    public int findAllBoardsCount() {
        log.info("[[ Repo - findAllBoardsCount ]]");

        /**
         * count(*) 함수의 경우 return 값이 Long 이므로
         * Board.class가 아닌 Long.class로 해주어야 한다.
         */
        Long result = em.createQuery("select count(board_id) from Board b where b.status != 'DELETED'", Long.class)
                .getSingleResult();

        return Math.toIntExact(result);
    }

    public int findBoardByKeywordCount(String keyword) {
        log.info("[[ Repo - findBoardByKeywordCount ]]");

        Long result = em.createQuery("select count(board_id) from Board b where b.title like :keyword" +
                                    " and b.status != 'DELETED'", Long.class)
                .setParameter("keyword", "%"+keyword+"%")
                .getSingleResult();

        return Math.toIntExact(result);
    }

    /**
     * List param 을 in 절 조건에 넣어서 여러개의 data 한번에 select 하기
     * @param board_id_list
     * @return
     */
    public List findBoardsByBoardIds(List<Long> board_id_list){
        log.info("[[ Repo - findBoardsByBoardIds ]]");

        List result = em.createQuery("select b from Board b where b.id in :id")
                .setParameter("id", board_id_list)
                .getResultList();

        return result;
    }
}
