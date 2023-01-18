package jpa.jpazone.controller;

import jpa.jpazone.controller.form.BoardForm;
import jpa.jpazone.controller.form.BoardListDto;
import jpa.jpazone.controller.form.ShowBoardForm;
import jpa.jpazone.domain.Board;
import jpa.jpazone.domain.Member;
import jpa.jpazone.service.BoardService;
import jpa.jpazone.service.CommentService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    /**
     * 게시글 생성 양식으로 이동
     * @param model
     * @return
     */
    @GetMapping("/board/new")
    public String createBoard(Model model, @SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false)Member loginMember){
        log.info("[[ createBoard ]]");
        BoardForm boardForm = new BoardForm();
        boardForm.setName(loginMember.getName());
        model.addAttribute("boardForm", boardForm);
        return "boards/createBoard";
    }

    /**
     * 게시글 생성
     * @param boardForm
     * @param result
     * @return
     */
    @PostMapping("/board/post")
    public String newBoard(@Valid @ModelAttribute("boardForm") BoardForm boardForm, BindingResult result){
        log.info("[[ Post - createBoard ]]");
        if(result.hasErrors()){
            log.info(String.valueOf(result.getFieldError()));
            return "boards/createBoard";
        }
        boardService.newBoard(boardForm);

        return "redirect:/boards";
    }

    /**
     * 게시글 리스트
     * @param model
     * @return
     */
    @GetMapping("/boards")
    public String boards(Model model,
                        @RequestParam(value = "offset", defaultValue = "0") int offset,
                        @RequestParam(value = "limit", defaultValue = "10") int limit
    ){
        log.info("[[ boards ]]");

        //페이지구현을 위한 offset과 limit을 param으로 게시글 가져오기
        List<Board> boards = boardService.findAllBoards(offset, limit);
        //Board 엔티티를 BoardListDto로 변환
        List<BoardListDto> boardList = boards.stream().map(BoardListDto::new).collect(Collectors.toList());
        //게시글 갯수를 통해 마지막 페이지 가져오기
        int lastPage = boardService.getLastPage(limit);

        model.addAttribute("lastPage", lastPage);
        model.addAttribute("boardList", boardList);

        return "boards/boards";
    }

    /**
     * 게시판 페이징
     * @param model
     * @param pageNum
     * @param limit
     * @return
     */
    @GetMapping("/boards/list")
    public String boardPaging(Model model,
                              @RequestParam("page")String pageNum,
                              @RequestParam(value = "limit", defaultValue = "10") int limit){
        log.info("[[ boardPaging ]]");
        log.info("pageNum => {}", pageNum);

        int page = Integer.parseInt(pageNum);
        int offset = 0;

        if(page != 0){
            offset = (page - 1) * limit;
        }

        //페이지구현을 위한 offset과 limit을 param으로 게시글 가져오기
        List<Board> boards = boardService.findAllBoards(offset, limit);
        //Board 엔티티를 BoardListDto로 변환
        List<BoardListDto> boardList = boards.stream().map(BoardListDto::new).collect(Collectors.toList());

        //게시글 갯수를 통해 마지막 페이지 가져오기
        int lastPage = boardService.getLastPage(limit);

        model.addAttribute("lastPage", lastPage);
        model.addAttribute("boardList", boardList);

        return "boards/boards";
    }

    /**
     * 선택한 게시글 확인하기
     * @param boardId
     * @param model
     * @return
     */
    @GetMapping("/board/post/{boardId}")
    public String board (@PathVariable("boardId")String boardId,
                         Model model,
                         @SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false)Member loginMember){
        log.info("boardId = {}", boardId);
        //String 으로 넘어온 boardId를 Long으로 변환
        Long id = Long.parseLong(boardId);

        //id로 Board 엔티티 가져오기
        Board board = boardService.findBoard(id);

        //board_id로 comment들 가져오기
        //List<Comment> comments = commentService.findAllCommentByBoardId(id);


        ShowBoardForm showBoardForm = ShowBoardForm.setBoardInfo(
                board.getId(), board.getTitle(), board.getWriter(),
                board.getContent(), board.getComments(), loginMember.getName());

        model.addAttribute("showBoardForm", showBoardForm);

        return "boards/showBoard";
    }

    /**
     * 게시글 수정
     * @param boardId
     * @param boardForm
     * @return
     */
    @PostMapping("/board/post/{boardId}")
    public String updateBoard(@PathVariable("boardId")Long boardId, @ModelAttribute("boardForm")BoardForm boardForm){
        log.info("[[ updateBoard ]]");
        boardService.updateBoard(boardId, boardForm.getName(), boardForm.getTitle(), boardForm.getContent());
        return "redirect:/boards";
    }

    /**
     * 검색으로 원하는 게시글 찾기
     * @param keyword
     */
    @GetMapping("/board/search")
    public String searchBoard(Model model, @RequestParam("keyword")String keyword){
        log.info("[[ searchBoard ]]");
        log.info("keyword => {}", keyword);

        List<Board> boards = boardService.findBoardByKeyword(keyword);
        model.addAttribute("boardList", boards);

        return  "boards/boards";
    }


}