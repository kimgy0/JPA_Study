package hellojpa.Chapter6;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlID;

@Entity
public class MemberProxy {
    private String username;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    //팀 객체를 지연로딩 합니다.
    @JoinColumn(name = "TEAM_ID")
    private TeamProxy team;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TeamProxy getTeam() {
        return team;
    }

    public void setTeam(TeamProxy team) {
        this.team = team;
    }
}
