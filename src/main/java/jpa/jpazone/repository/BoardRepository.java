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


    public List<Board> findAllBoards() {
        log.info("[[ Repo - findAllBoards ]]");

        return  em.createQuery("select b from Board b", Board.class)
                .getResultList();
    }

    public Board findBoard(Long boardId) {
        log.info("[[ Repo - findBoard ]]");

        return em.find(Board.class, boardId);
    }

    public List<Board> findBoardByKeyword(String keyword) {
        return em.createQuery("select b from Board b where b.title like :keyword", Board.class)
                .setParameter("keyword", "%"+keyword+"%")
                .getResultList();
    }
}
