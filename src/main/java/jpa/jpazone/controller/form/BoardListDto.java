package jpa.jpazone.controller.form;

import jpa.jpazone.domain.Board;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class BoardListDto {

    private Long board_id;
    private String title; //제목
    private String writer; //작성자
    private LocalDateTime write_date; //작성시간
    private LocalDateTime update_date; //수정시간
    private String content; //내용

    public BoardListDto(Long board_id, String title, String writer,
                        LocalDateTime write_date, LocalDateTime update_date, String content) {
        this.board_id = board_id;
        this.title = title;
        this.writer = writer;
        this.write_date = write_date;
        this.update_date = update_date;
        this.content = content;
    }

    public BoardListDto(Board board){
        board_id = board.getId();
        title = board.getTitle();
        writer = board.getWriter();
        write_date = board.getWrite_date();
        update_date = board.getUpdate_date();
        content = board.getContent();
    }
}
