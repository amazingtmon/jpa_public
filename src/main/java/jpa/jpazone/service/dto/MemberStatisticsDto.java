package jpa.jpazone.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberStatisticsDto {

    private int user_id;
    private String name;
    private int board_count;
    private int comment_count;
    private int bookmark_count;
    private int news_count;
    private int report_count;

    public MemberStatisticsDto(int user_id, String name, int board_count, int comment_count
                            ,int bookmark_count, int news_count, int report_count) {
        this.user_id = user_id;
        this.name = name;
        this.board_count = board_count;
        this.comment_count = comment_count;
        this.bookmark_count = bookmark_count;
        this.news_count = news_count;
        this.report_count = report_count;
    }
}
