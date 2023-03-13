package jpa.jpazone.domain;

import jpa.jpazone.domain.enumpackage.RoleGroup;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRole {

    @Id @GeneratedValue
    @Column(name = "mr_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleGroup role;
    private LocalDateTime mr_regi_date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    // 연관관계 메서드 //
    public void setMember(Member member){
        this.member = member;
        member.getMemberRoles().add(this);
    }

    public MemberRole(Member member, RoleGroup roleGroup) {
        this.setMember(member);
        this.mr_regi_date = LocalDateTime.now();
        this.role = roleGroup;
    }
}
