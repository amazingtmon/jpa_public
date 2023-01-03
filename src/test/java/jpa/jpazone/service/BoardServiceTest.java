package jpa.jpazone.service;

import jpa.jpazone.controller.form.BoardForm;
import jpa.jpazone.domain.Board;
import jpa.jpazone.domain.Member;
import jpa.jpazone.repository.BoardRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    MemberService memberService;

    @Autowired
    BoardRepository boardRepository;

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

}