package jpa.jpazone.service;

import jpa.jpazone.controller.form.BoardForm;
import jpa.jpazone.domain.Board;
import jpa.jpazone.domain.BoardStatus;
import jpa.jpazone.domain.Member;
import jpa.jpazone.repository.BoardRepository;
import jpa.jpazone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long newBoard(BoardForm boardForm){
        log.info("[[ Service - newBoard ]]");
        //Member 엔티티조회
        List<Member> memberByName = memberRepository.findMemberByName(boardForm.getName());
        Optional<Member> optionalMember = memberByName.stream().filter(m -> m.getName().equals(boardForm.getName())).findFirst();
        Member member = optionalMember.orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다."));

        //게시판생성
        Board board = new Board(member, boardForm.getTitle(), boardForm.getName(), LocalDateTime.now(), boardForm.getContent());

        //DB에 저장
        boardRepository.save(board);

        return board.getId();
    }

    public List<Board> findAllBoards(int offset, int limit) {
        log.info("[[ Service - findAllBoards ]]");
        List<Board> boards = boardRepository.findAllBoards(offset, limit);
        //BoardStatus가 DELETED 가 아닌것만 필터하여 리턴
        List<Board> boardStatus_ExistList = boards.stream().filter(b -> b.getStatus() != BoardStatus.DELETED).collect(Collectors.toList());

        return boardStatus_ExistList;
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

    @Transactional
    public void deleteBoard(Long boardId){
        log.info("[[ Service - deleteBoard ]]");
        Board board = boardRepository.findBoard(boardId);
        board.delete();
    }

    public List<Board> findBoardByKeyword(String keyword, int offset, int limit) {
        log.info("[[ Service - findBoardByKeyword ]]");
        List<Board> boardByKeyword = boardRepository.findBoardByKeyword(keyword, offset, limit);
        //BoardStatus가 DELETED 가 아닌것만 필터하여 리턴
        List<Board> boards = boardByKeyword.stream().filter(b -> b.getStatus() != BoardStatus.DELETED).collect(Collectors.toList());
        return boards;
    }

    public int findAllBoardsCount() {
        log.info("[[ Service - findAllBoardsCount ]]");

        int totalCount = boardRepository.findAllBoardsCount();

        return totalCount;
    }

    public int getLastPage(int limit) {
        log.info("[[ Service - getLastPage ]]");

        int totalCount = boardRepository.findAllBoardsCount();
        double size = totalCount;
        int lastPage = (int) Math.ceil(size / limit);
        log.info("lastPage => {}", lastPage);

        return lastPage;
    }

    public int getLastPageSearchKeyword(int limit, String keyword) {
        log.info("[[ Service - getLastPageSearchKeyword ]]");

        int totalCount = boardRepository.findBoardByKeywordCount(keyword);
        double size = totalCount;
        int lastPage = (int) Math.ceil(size / limit);
        log.info("keyword board lastPage => {}", lastPage);

        return lastPage;
    }
}
