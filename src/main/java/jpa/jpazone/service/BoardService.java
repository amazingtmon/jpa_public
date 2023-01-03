package jpa.jpazone.service;

import jpa.jpazone.controller.form.BoardForm;
import jpa.jpazone.domain.Board;
import jpa.jpazone.domain.Member;
import jpa.jpazone.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberService memberService;

    @Transactional
    public Long newBoard(BoardForm boardForm){
        log.info("[[ Service - newBoard ]]");
        //Member 엔티티조회
        Member member = memberService.findMemberByName(boardForm.getName());

        //게시판생성
        Board board = new Board(member, boardForm.getTitle(), boardForm.getName(), LocalDateTime.now(), boardForm.getContent());

        //DB에 저장
        boardRepository.save(board);

        return board.getId();
    }

    public List<Board> findAllBoards() {
        log.info("[[ Service - findAllBoards ]]");
        return boardRepository.findAllBoards();
    }

    public Board findBoard(Long boardId) {
        log.info("[[ Service - findBoard ]]");

        return boardRepository.findBoard(boardId);
    }

    @Transactional
    public void updateBoard(Long boardId, String name, String title, String content) {
        log.info("[[ Service - updateBoard ]]");
        Board board = boardRepository.findBoard(boardId);
        board.change(name, title, content, LocalDateTime.now());
    }
}
