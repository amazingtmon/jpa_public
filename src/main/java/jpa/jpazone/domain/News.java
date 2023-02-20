package jpa.jpazone.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class News {

    @Id @GeneratedValue
    @Column(name = "news_id")
    private Long id;

    private String article_title;
    private String article_url;
    private LocalDateTime article_regiTime;
    private LocalDateTime article_deleteTime;
    boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    public News (String title, String url, Member member) {
        this.article_title = title;
        this.article_url = url;
        this.article_regiTime = LocalDateTime.now();
        this.isDeleted = false;
        this.setMember(member);
    }

    //==연관관계 메서드==//
    public void setMember(Member member){
        this.member = member;
        member.getNews().add(this);
    }

}
