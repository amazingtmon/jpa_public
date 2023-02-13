package jpa.jpazone.service;

import jpa.jpazone.domain.Bookmark;
import jpa.jpazone.repository.BookmarkRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BookmarkServiceTest {

    @Autowired
    BookmarkRepository bookmarkRepository;

    @Autowired
    BookmarkService bookmarkService;

    @Test
    public void 보드아이디로북마크찾기() throws Exception {
        // given
        Long board_id = 101L; //board dummyData id
        Long user_id = 1L; //member id

        // when
        boolean result = bookmarkService.findBookmarkByBoardIdAndMemberId(board_id, user_id);
        System.out.println("result => "+ result);

        // then

    }

}