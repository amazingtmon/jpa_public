package jpa.jpazone.service;

import jpa.jpazone.domain.*;
import jpa.jpazone.repository.BoardRepository;
import jpa.jpazone.repository.BookmarkRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BookmarkServiceTest {

    @Autowired
    BookmarkRepository bookmarkRepository;
    @Autowired
    BookmarkService bookmarkService;
    @Autowired
    MemberService memberService;
    @Autowired
    BoardService boardService;
    @Autowired
    BoardRepository boardRepository;

    @Test
    public void 보드아이디로북마크찾기() throws Exception {
        // given
        Long board_id = 101L; //board dummyData id
        Long user_id = 2L; //member dummyData id
        boolean bookMarked = false;

        // when
        Optional<Bookmark> result = bookmarkService.findBookmarkByBoardIdAndMemberId(board_id, user_id);

        if(result.isPresent()){
            System.out.println(" => "+ result.get().getId());
            Bookmark bookmark = result.get();
            //Bookmark 테이블에 data 가 존재하면서, isBookmarked 값이 true 일 경우
            if(bookmark.isBookmarked()){
                throw new RuntimeException("이미 북마크한 게시물 입니다.");
            }

            bookmarkService.updateBookmark(bookmark);
            bookMarked = bookmark.isBookmarked();
        }

        System.out.println("bookMarked => "+ bookMarked);
        System.out.println("result => "+ result);
        // then
        //assertEquals();

    }
    
    @Test
    public void 북마크취소() throws Exception {
        // given
        Long board_id = 101L; //board dummyData id
        Long user_id = 1L; //member dummyData id
        // when

        //Bookmark 테이블에 데이터 존재여부 체크
        Optional<Bookmark> optionalBookmark = bookmarkService.findBookmarkByBoardIdAndMemberId(board_id, user_id);
        if(!optionalBookmark.isPresent()){
            throw new RuntimeException("no bookmark data");
        }

        //Bookmark 엔티티
        Bookmark bookmark = optionalBookmark.get();
        bookmarkService.cancelBookmark(bookmark);

        // then
        assertEquals(false, bookmark.isBookmarked());
    }

    @Test
    public void 연관관계매핑된엔티티아이디로데이터Select하기() throws Exception {
        // given
        Long board_id = 106L; //board dummyData id
        Long user_id = 1L; //member dummyData id

        // when
        Optional<Bookmark> result = bookmarkService.findBookmarkByBoardIdAndMemberId(board_id, user_id);
        System.out.println("result => "+ result.isPresent());

        // then
    }

    @Test
    public void 멤버아이디와BookMarkItem으로데이터Select() throws Exception {
        // given
        Long user_id = 1L; //member dummyData id

        // when
        List<Bookmark> result = bookmarkRepository.findAllByMemberAndItem(user_id, BookMarkItem.BOARD);
        result.stream().forEach( bm -> {
            System.out.println("bm id => "+bm.getId());
        });

        result.stream().filter( bm -> bm.getBoard().getStatus() != BoardStatus.DELETED )
                .forEach(b -> {
                    System.out.println("filter board_id, status => "+b.getBoard().getId()+", "+b.getBoard().getStatus());
                });

        // then
    }

}