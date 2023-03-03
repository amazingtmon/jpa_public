package jpa.jpazone.service;

import jpa.jpazone.domain.Board;
import jpa.jpazone.domain.enumpackage.BookMarkItem;
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

        // Member 가 bookmark 한 게시물 리스트 가져오기
        List<Bookmark> bookmarks = bookmarkRepository.findBookmarkByBoardIdAndMemberId(board_id, user_id);
        // bookmark 리스트에서 삭제하려고 선택한 게시물만 필터링
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

    public List<Bookmark> findAllByMemberAndItem(Long user_id, BookMarkItem bmi_item) {
        log.info("[[ Service - findAllByMemberAndItem ]]");
        return bookmarkRepository.findAllByMemberAndItem(user_id, bmi_item);
    }

    @Transactional
    public void deleteBookmarkEver(Bookmark bookmark) {
        log.info("[[ Service - deleteBookmarkEver ]]");
        bookmarkRepository.deleteBookmarkEver(bookmark);
    }
}
