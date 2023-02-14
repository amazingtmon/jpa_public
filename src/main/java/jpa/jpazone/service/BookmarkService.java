package jpa.jpazone.service;

import jpa.jpazone.domain.Board;
import jpa.jpazone.domain.Bookmark;
import jpa.jpazone.domain.Member;
import jpa.jpazone.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public Long createBookmark(Board board, Member member) {
        log.info("[[ Service - createBookmark ]]");

        Bookmark bookMark = new Bookmark(board, member);
        bookmarkRepository.save(bookMark);

        return bookMark.getId();
    }

    public Optional<Bookmark> findBookmarkByBoardIdAndMemberId(Long board_id, Long user_id){
        log.info("[[ Service - findBookmarkByBoardIdAndMemberId ]]");
        List<Bookmark> bookmarks = bookmarkRepository.findBookmarkByBoardIdAndMemberId(board_id, user_id);
        Optional<Bookmark> bookmark = bookmarks.stream().filter(bm -> bm.getBookmark_item_id() == board_id)
                .findFirst();
        return bookmark;
    }

    @Transactional
    public void updateBookmark(Bookmark bookmark) {
        log.info("[[ Service - updateBookmark ]]");

        bookmark.change();
    }

    @Transactional
    public void cancelBookmark(Bookmark bookmark) {
        log.info("[[ Service - cancelBookmark ]]");
        bookmark.cancel();
    }
}
