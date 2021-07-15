package hellojpa.Chapter3;

import javax.persistence.*;

@Entity
public class MemberRelationMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MEMBER_ID")
    private Long id;

    @Column(name="USERNAME")
    private String username;

//    @Column(name="TEAM_ID")
//    private Long teamId;
    @ManyToOne
    @JoinColumn(name="TEAM_ID")
    private Team team;
    /*
        연관관계의 주인은 항상 다 쪽으로 들고가는 것이 현명하다.
        연관관계의 주인만이 데이터베이스 연관관계와 매핑되고 외래키를 관리한다.
        외래키를 관리하는 객체는 데이터베이스에 영향을 줘서 수정 삽입 삭제 등등이 가능하다.
        mappedby = "" 로 연관관계 주인을 정한다.
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
