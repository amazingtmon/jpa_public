package jpa.jpazone.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ContentsCountDto {

    private int memberCount;
    private int boardsCount;
    private int commentsCount;
    private int bookmarksCount;
    private int newsCount;
    private int reportsCount;

    public ContentsCountDto(int memberCount, int boardsCount, int commentsCount, int bookmarksCount, int newsCount, int reportsCount) {
        this.memberCount = memberCount;
        this.boardsCount = boardsCount;
        this.commentsCount = commentsCount;
        this.bookmarksCount = bookmarksCount;
        this.newsCount = newsCount;
        this.reportsCount = reportsCount;
    }
}
