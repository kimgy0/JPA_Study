package hellojpa.Chapter6;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TeamProxy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEAM_ID")
    private Long id;

    private String teamName;

    @OneToMany(mappedBy = "team", cascade = CascadeType.PERSIST)
    private List<MemberProxy> members = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<MemberProxy> getMembers() {
        return members;
    }

    public void setMembers(List<MemberProxy> members) {
        this.members = members;
    }
}
