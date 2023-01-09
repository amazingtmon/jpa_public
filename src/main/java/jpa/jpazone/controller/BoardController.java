package jpa.jpazone.controller;

import jpa.jpazone.controller.form.BoardForm;
import jpa.jpazone.controller.form.ShowBoardForm;
import jpa.jpazone.domain.Board;
import jpa.jpazone.domain.Comment;
import jpa.jpazone.service.BoardService;
import jpa.jpazone.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

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
    public String createBoard(Model model){
        log.info("[[ createBoard ]]");
        model.addAttribute("boardForm", new BoardForm());
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
    public String boards(Model model){
        log.info("[[ boards ]]");

        List<Board> boardList = boardService.findAllBoards();
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
    public String board (@PathVariable("boardId")String boardId, Model model){
        log.info("[[ get board, id ="+ boardId +" ]]");
        log.info("boardId = {}", boardId);
        //String 으로 넘어온 boardId를 Long으로 변환
        Long id = Long.parseLong(boardId);

        //id로 Board 엔티티 가져오기
        Board board = boardService.findBoard(id);

        //board_id로 comment들 가져오기
        //List<Comment> comments = commentService.findAllCommentByBoardId(id);

        //showBoardForm에 값 set
        /*BoardForm boardForm = new BoardForm();
        boardForm.setBoard_id(board.getId());
        boardForm.setName(board.getWriter());
        boardForm.setTitle(board.getTitle());
        boardForm.setContent(board.getContent());*/

        ShowBoardForm showBoardForm = ShowBoardForm.setBoardInfo(
                board.getId(), board.getTitle(), board.getWriter(), board.getContent(), board.getComments());

        model.addAttribute("showBoardForm", showBoardForm);

        return "boards/showBoard";
    }

    @PostMapping("/board/post/{boardId}")
    public String updateBoard(@PathVariable("boardId")Long boardId, @ModelAttribute("boardForm")BoardForm boardForm){
        log.info("[[ updateBoard ]]");
        boardService.updateBoard(boardId, boardForm.getName(), boardForm.getTitle(), boardForm.getContent());
        return "redirect:/boards";
    }

}