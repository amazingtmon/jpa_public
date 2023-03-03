package jpa.jpazone.api;

import jpa.jpazone.controller.form.BoardForm;
import jpa.jpazone.controller.form.BoardListDto;
import jpa.jpazone.controller.form.ShowBoardForm;
import jpa.jpazone.domain.Board;
import jpa.jpazone.domain.enumpackage.BoardStatus;
import jpa.jpazone.domain.Member;
import jpa.jpazone.repository.BoardRepository;
import jpa.jpazone.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @GetMapping("/api/boards/list")
    public Result boardList(@RequestParam(value = "offset", defaultValue = "0") int offset,
                            @RequestParam(value = "limit", defaultValue = "10") int limit
    ){
        log.info("[[ RestController - boardList ]]");
        List<Board> boards = boardRepository.findAllBoards(offset, limit);
        //Board 리스트를 BoardListDto로 변환
        List<BoardListDto> boardList = boards.stream().map(BoardListDto::new).collect(Collectors.toList());
        Result result = new Result<>(boardList.size() ,boardList);
        return result;
    }

    @PostMapping("/api/board/post")
    @Transactional
    public ResponseEntity<Object> createBoard(@RequestBody BoardForm boardForm){
        log.info("[[ RestController - createBoard ]]");
        //작성자 이름으로 Member 엔티티 가져오기
        List<Member> memberByName = memberRepository.findMemberByName(boardForm.getName());
        Optional<Member> optionalMember = memberByName.stream().findFirst();

        //Member 엔티티 확인
        Member member = optionalMember.orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다."));
        //Board 엔티티에 Member, BoardForm 값 세팅
        Board board = new Board(member, boardForm.getTitle(), boardForm.getName(), LocalDateTime.now(), boardForm.getContent());
        //Board 저장
        boardRepository.save(board);

        return new ResponseEntity<>(new CreateBoardDto(board), HttpStatus.CREATED);
    }

    @PutMapping("/api/board/post/{boardId}")
    @Transactional
    public ResponseEntity<Object> updateBoard(@PathVariable("boardId")Long boardId,
                                      @RequestBody ShowBoardForm showBoardForm){
        log.info("[[ RestController - updateBoard ]]");

        //id에 해당하는 Board 엔티티 찾기
        Board board = boardRepository.findBoard(boardId);
        //수정된 내용 적용
        board.change(showBoardForm.getName(), showBoardForm.getTitle(),
                    showBoardForm.getContent(), LocalDateTime.now());

        return new ResponseEntity<>(new UpdateBoardDto(board), HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/api/board/post/{boardId}")
    public ResponseEntity<Object> deleteBoard(@PathVariable("boardId")Long boardId){
        log.info("[[ RestController - deleteBoard ]]");

        Board board = boardRepository.findBoard(boardId);
        board.delete();

        return new ResponseEntity<>(new BoardDto(board), HttpStatus.NO_CONTENT);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }
    @Data
    private class BoardDto {
        private Long board_id;
        private String name;
        private String title;
        private String content;
        private BoardStatus status;

        public BoardDto(Board board){
            this.board_id = board.getId();
            this.name = board.getWriter();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.status = board.getStatus();
        }
    }

    @Data
    private class CreateBoardDto {
        private String name;
        private String title;
        private String content;

        public CreateBoardDto(Board board){
            this.name = board.getWriter();
            this.title = board.getTitle();
            this.content = board.getContent();
        }
    }

    @Data
    private class UpdateBoardDto {

        private Long board_id;
        private String name;
        private String title;
        private String content;
        private LocalDateTime write_date;
        private LocalDateTime update_date;

        public UpdateBoardDto(Board board) {
            this.board_id = board.getId();
            this.name = board.getWriter();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.write_date = board.getWrite_date();
            this.update_date = board.getUpdate_date();
        }
    }
}
