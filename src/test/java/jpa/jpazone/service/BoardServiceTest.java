package jpa.jpazone.service;

import jpa.jpazone.controller.form.BoardForm;
import jpa.jpazone.controller.form.BoardListDto;
import jpa.jpazone.domain.Board;
import jpa.jpazone.domain.BoardStatus;
import jpa.jpazone.domain.Member;
import jpa.jpazone.repository.BoardRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BoardServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    BoardService boardService;

    @Test
    public void 게시판생성 () throws Exception {
        // given
        BoardForm boardForm = new BoardForm();
        boardForm.setName("yang");
        boardForm.setTitle("test111");
        boardForm.setContent("testtesttesttesttesttesttest");

        // when
        Member yang = memberService.findMemberByName("yang");
        Board board = new Board(yang, boardForm.getTitle(), boardForm.getName(), LocalDateTime.now(), boardForm.getContent());

        boardRepository.save(board);

        // then
        assertEquals(1, board.getId());
    }

//    @Test
//    public void 특정게시판내용및댓글가져오기(
//            @SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false)Member loginMember) throws Exception {
//        // given
//        Long boardId = 2L;
//
//        Board board = boardRepository.findBoard(boardId);
//
//        // when
//        ShowBoardForm showBoardForm = ShowBoardForm.setBoardInfo(
//                board.getId(), board.getTitle(), board.getWriter(),
//                board.getContent(), board.getComments(), loginMember.getName());
//
//        System.out.println("== get comments == "+showBoardForm.getCommentForms().size());
//        List<ShowCommentForm> commentForms = showBoardForm.getCommentForms();
//        System.out.println("== getComment_content == "
//                +showBoardForm.getCommentForms().get(0).getComment_id()+", "
//                +showBoardForm.getCommentForms().get(0).getComment_content()+", "
//                +showBoardForm.getCommentForms().get(0).getName()+", "
//                +showBoardForm.getCommentForms().get(0).getWrite_date()+", "
//                +showBoardForm.getCommentForms().get(0).getUpdate_date()
//        );
//        // then
//    }

    @Test
    public void 검색한게시물가져오기() throws Exception {
        // given
        String keyword = "aaa";
        int offset = 0;
        int limit = 10;

        // when
        List<Board> boards = boardRepository.findBoardByKeyword(keyword, offset, limit);
        int count = boardRepository.findBoardByKeywordCount(keyword);
        System.out.println("board size => "+boards.size());
        System.out.println("count value => "+count);

        // then
        assertEquals(boards.size(), count);

    }

    @Test
    public void 게시물삭제() throws Exception {
        // given
        Long id = 4L;

        // when

        //게시물 삭제 = 게시물 status == DELTED로 변경
        boardService.deleteBoard(id);
        Board board2 = boardRepository.findBoard(id);
        BoardStatus status2 = board2.getStatus();
        String modified_status = status2.name();

        // then
        assertEquals("DELETED", modified_status);

    }

    @Test
    public void 게시물상태가EXIST인것만가져오기() throws Exception {
        // given
        int offset = 0;
        int limit = 10;
        Long boardId = 4L;
        List<Board> boards = boardRepository.findAllBoards(offset, limit);
        Board expected_board = boardRepository.findBoard(boardId);

        // when
        List<Board> boardList = boards.stream().filter(b -> b.getStatus() != BoardStatus.DELETED).collect(Collectors.toList());
        System.out.println("boardList => "+ boardList.getClass());
        System.out.println("boardList.size() => "+ boardList.size());

        List<BoardListDto> transBoardDto = boardList.stream().map(BoardListDto::new).collect(Collectors.toList());
        System.out.println("transBoardDto => "+ transBoardDto.getClass());
        System.out.println("transBoardDto.size() => "+ transBoardDto.size());

        // then
        assertEquals(expected_board.getTitle(), transBoardDto.get(0).getTitle());
    }

}