package jpa.jpazone.controller;

import jpa.jpazone.controller.form.BookMarkedBoardDto;
import jpa.jpazone.controller.form.MemberInfoForm;
import jpa.jpazone.controller.form.MyArticleDto;
import jpa.jpazone.domain.*;
import jpa.jpazone.domain.enumpackage.BoardStatus;
import jpa.jpazone.domain.enumpackage.BookMarkItem;
import jpa.jpazone.service.BookmarkService;
import jpa.jpazone.service.MemberService;
import jpa.jpazone.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;
    private final BookmarkService bookmarkService;
    private final NewsService newsService;

    @GetMapping("/myPage/info")
    public String myPage(Model model,
                         @SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false)Member loginMember){
        log.info("[[ myPage ]]");

        MemberInfoForm memberInfoForm = new MemberInfoForm();
        memberInfoForm.setName(loginMember.getName());

        model.addAttribute("memberInfoForm", memberInfoForm);
        return "myPage/myPage";
    }

    @PostMapping("/myPage/info")
    public String myPage(@Valid @ModelAttribute("memberInfoForm")MemberInfoForm memberInfoForm, BindingResult result){
        log.info("[[ update member info ]]");

        if(result.hasErrors()){
            log.info("myPage error => {}", result.getFieldError());
            return "myPage/myPage";
        }

        memberService.updateMemberInfo(memberInfoForm.getName(), memberInfoForm.getPassword());

        return "redirect:/mainHome";
    }

    @GetMapping("/myPage/board")
    public String myBoard(Model model,
                          @SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member loginMember){
        log.info("[[ myBoard ]]");

        //Member ?????????
        Member member = memberService.findMemberById(loginMember.getId());
        //Bookmark ??? Board ?????????
        List<Bookmark> boardList = bookmarkService.findAllByMemberAndItem(member.getId(), BookMarkItem.BOARD);
        // ????????? Board ????????? ??? BoardStatus == EXIST ??? ?????? isBookmarked == true ??? ?????? ?????????
        List<Bookmark> bookMarkedBoards = boardList.stream().filter(bm -> bm.getBoard().getStatus() != BoardStatus.DELETED)
                .filter( bookmark -> bookmark.isBookmarked())
                .collect(Collectors.toList());
        //Bookmark ???????????? BookMarkedBoardDto ??? ??????
        List<BookMarkedBoardDto> boardDtos = bookMarkedBoards.stream().map(BookMarkedBoardDto::new).collect(Collectors.toList());

        model.addAttribute("boardDtos", boardDtos);

        return "myPage/myBoard";
    }

    @GetMapping("/myPage/article")
    public String myArticle(Model model,
                            @SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member loginMember){
        log.info("[[ myArticle ]]");

        //Member ?????????
        Member member = memberService.findMemberById(loginMember.getId());
        //News ?????????
        List<News> articlesList = newsService.findAllArticles(member.getId());
        //?????? ??????????????? isDeleted == false ??? ?????? ?????????
        List<News> allArticles = articlesList.stream().filter(a -> !a.isDeleted()).collect(Collectors.toList());
        //News ???????????? MyArticleDto ??? ??????
        List<MyArticleDto> articleDtos = allArticles.stream().map(MyArticleDto::new).collect(Collectors.toList());

        model.addAttribute("articleDtos", articleDtos);

        return "myPage/myArticle";
    }
}
