package jpa.jpazone.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //여러개의 게시글이 하나의 회원에게 해당
    @JoinColumn(name = "user_id")//매핑하려는 컬럼, FK
    private Member member;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    private String title; //제목
    private String writer; //작성자
    private LocalDateTime write_date; //작성시간
    private LocalDateTime update_date; //수정시간
    private String content; //내용


    public Board(Member member, String title, String writer, LocalDateTime write_date, String content) {
        this.member = member;
        this.title = title;
        this.writer = writer;
        this.write_date = write_date;
        this.content = content;
    }

    /**
     * 게시글 수정 메소드
     *
     * @param name
     * @param title
     * @param content
     * @param update_date
     */
    public void change(String name, String title, String content, LocalDateTime update_date) {
        this.writer = name;
        this.title = title;
        this.content = content;
        this.update_date = update_date;
    }
}
