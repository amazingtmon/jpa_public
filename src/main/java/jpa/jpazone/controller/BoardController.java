package jpa.jpazone.controller;

import jpa.jpazone.controller.form.BoardForm;
import jpa.jpazone.controller.form.BoardListDto;
import jpa.jpazone.controller.form.ShowBoardForm;
import jpa.jpazone.domain.Board;
import jpa.jpazone.domain.Member;
import jpa.jpazone.service.BoardService;
import jpa.jpazone.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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
    public String board (@PathVariable("boardId")Long boardId,
                         Model model,
                         @SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false)Member loginMember){
        log.info("boardId = {}", boardId);

        //id로 Board 엔티티 가져오기
        Board board = boardService.findBoard(boardId);

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
     * @param showBoardForm
     * @return
     */
    @PostMapping("/board/post/{boardId}")
    public String updateBoard(@PathVariable("boardId")Long boardId,
                              @Valid @ModelAttribute("showBoardForm")ShowBoardForm showBoardForm,
                              BindingResult result,
                              RedirectAttributes redirectAttributes){
        log.info("[[ updateBoard ]]");

        if(result.hasErrors()){
            log.info("error => {}",String.valueOf(result.getFieldError()));
            redirectAttributes.addFlashAttribute("errors", result.getFieldError().getDefaultMessage());
            return "redirect:/board/post/"+boardId;
        }

        boardService.updateBoard(boardId, showBoardForm.getName(), showBoardForm.getTitle(), showBoardForm.getContent());
        return "redirect:/boards";
    }

    /**
     * 게시글 삭제
     * Board 테이블에서 실제 게시글 data를 삭제하는것이 아니라
     * 게시글의 status 값을 EXIST => DELETED로 변경
     * @param boardId
     * @return
     */
    @GetMapping("/board/status")
    public String deleteBoard(@RequestParam("post")String boardId){
        log.info("[[ deleteBoard ]]");

        //String board_id를 Long 타입으로 변환
        Long id = Long.valueOf(boardId);
        boardService.deleteBoard(id);

        return "redirect:/boards";
    }

    /**
     * 검색으로 원하는 게시글 찾기
     * @param keyword
     */
    @GetMapping("/board/word")
    public String searchBoard(Model model,
                              @RequestParam("keyword")String keyword,
                              @RequestParam(value = "offset", defaultValue = "0") int offset,
                              @RequestParam(value = "limit", defaultValue = "10") int limit
    ){
        log.info("[[ searchBoard ]]");
        log.info("keyword => {}", keyword);

        List<Board> boards = boardService.findBoardByKeyword(keyword, offset, limit);
        //Board 엔티티를 BoardListDto로 변환
        List<BoardListDto> boardList = boards.stream().map(BoardListDto::new).collect(Collectors.toList());
        //검색한 게시글의 갯수로 마지막 페이지 가져오기
        int lastPage = boardService.getLastPageSearchKeyword(limit, keyword);

        model.addAttribute("keyword", keyword);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("boardList", boardList);

        return  "boards/keywordBoards";
    }

    /**
     * 검색으로 찾은 게시글 페이징
     * @param model
     * @param keyword
     * @param pageNum
     * @param limit
     * @return
     */
    @GetMapping("/board/page")
    public String searchBoardPaging(Model model,
                              @RequestParam("keyword")String keyword,
                              @RequestParam("page")String pageNum,
                              @RequestParam(value = "limit", defaultValue = "10") int limit
    ){
        log.info("[[ searchBoardPaging ]]");

        int page = Integer.parseInt(pageNum);
        int offset = 0;

        if(page != 0){
            offset = (page - 1) * limit;
        }

        List<Board> boards = boardService.findBoardByKeyword(keyword, offset, limit);
        //Board 엔티티를 BoardListDto로 변환
        List<BoardListDto> boardList = boards.stream().map(BoardListDto::new).collect(Collectors.toList());
        //검색한 게시글의 갯수로 마지막 페이지 가져오기
        int lastPage = boardService.getLastPageSearchKeyword(limit, keyword);

        model.addAttribute("keyword", keyword);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("boardList", boardList);

        return  "boards/keywordBoards";
    }

}