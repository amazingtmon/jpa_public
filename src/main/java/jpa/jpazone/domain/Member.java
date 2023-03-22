package jpa.jpazone.domain;

import jpa.jpazone.domain.enumpackage.BanStandard;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String name;
    private String password;

    @ColumnDefault("0")
    private Integer reported_count;

    @ColumnDefault("false")
    private Boolean isBanned;

    private LocalDateTime ban_end_time;

    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<News> news = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST)
    private List<MemberRole> memberRoles = new ArrayList<>();

    public Member(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @PrePersist
    public void prePersist(){
        this.reported_count = this.reported_count == null ? 0 : this.reported_count;
        this.isBanned = this.isBanned == null ? false : this.isBanned;
    }

    //==비즈니스 메서드==//
    public void change(String password) {
        this.password = password;
    }


    public void decideBanMember(){
        this.reported_count ++;
        int count = this.reported_count % BanStandard.COUNT.getCount();
        if(count == 0){
            this.isBanned = true;
            LocalDateTime time = LocalDateTime.now();
            this.ban_end_time = time.plusMinutes(BanStandard.MINUTE.getCount());
        }
    }
}

