package jpa.jpazone.repository;

import jpa.jpazone.domain.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save(Comment comment) {
        log.info("[[ Repo - save comment ]]");
        em.persist(comment);
    }

    public Comment findComment(Long comment_id){
        log.info("[[ findComment ]]");

        return em.find(Comment.class, comment_id);
    }

    public int findAllCommentsCount(){
        log.info("[[ Repo - findAllCommentsCount ]]");
        Long result = em.createQuery("select count(*) from Comment c", Long.class)
                .getSingleResult();
        return Math.toIntExact(result);
    }

    /**
     * 선택한 게시판 id로 해당게시판 parent 댓글 모두 가져오기
     * 테스트 결과 org.hibernate.QueryException: could not resolve property:
     * board_id of: jpa.jpazone.domain.Board [select c from jpa.jpazone.domain.Comment c where c.board.board_id = :boardId]
     * 와 같은 에러 발생.
     * where 절에서 FK와 관련된 엔티티를 한번더 참조해야 한다고 하여 [[ where c.board.board_id ]] 로 수정하여 테스트 해보았으나,
     * 똑같은 Exception 발생.
     * 정확한 원인 확인 불가.
     * =======================================================================
     * 2023-02-14 where 절의 문구를 [[ where c.board.id ]] 로 변경하여 해결.
     * @param boardId
     * @return
     */
    public List<Comment> findAllParentCommentsByBoardId(Long boardId) {
        log.info("[[ Repo - findAllParentCommentsByBoardId ]]");
        return em.createQuery("select c from Comment c " +
                        "where c.board.id = :boardId and deep = 0", Comment.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }

    public List<Comment> findAllChildCommentsByBoardId(Long boardId) {
        log.info("[[ Repo - findAllChildCommentsByBoardId ]]");

        return em.createQuery("select c from Comment c" +
                        " where c.board.id = :boardId" +
                        " and c.deep = 1", Comment.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }
}
