package jpa.jpazone.service;

import jpa.jpazone.domain.Board;
import jpa.jpazone.domain.Comment;
import jpa.jpazone.repository.CommentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CommentServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @Test
    public void 게시판댓글찾기() throws Exception {
        // given
        Long id = Long.valueOf(2);
        Board board = boardService.findBoard(id);
        System.out.println("== get board == "+board.getComments().size());
        System.out.println("board 1 == "+board.getComments().get(0).getComment_content());
        System.out.println("board 2 == "+board.getComments().get(1).getComment_content());

        // when

        // then
        //assertEquals("yang", board.getWriter());
    }

//    @Test
//    public void 게시판에달린댓글들찾기() throws Exception {
//        // given
//        Long board_id = 101L;
//
//        // when
//        List<Comment> allComments = commentService.findAllParentCommentsByBoardId(board_id);
//        allComments.stream().forEach(comment -> {
//            System.out.println("comment => "+comment.getComment_content());
//            System.out.println("comment getChildList => "+comment.getChildList().size());
//        });
//
//        // then
//    }
//
//    @Test
//    public void board_id와deep으로_childComments찾기() throws Exception {
//        // given
//        Long board_id = 101L;
//
//        // when
//        List<Comment> childComments = commentService.findAllChildCommentsByBoardId(board_id);
//        childComments.stream().forEach( ch -> {
//            System.out.println("childComment => "+ch.getComment_content());
//        });
//
//        // then
//    }

    @Test
    public void 게시글의모든댓글들가져오기() throws Exception {
        // given
        Long board_id = 101L;
        List<Comment> allComments = commentRepository.findBoardComments(board_id);

        System.out.println("=====================================================");

        // when

        // then
    }
}