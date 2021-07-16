package hellojpa.Chapter4.OneToMany;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TeamOneToMany {
    @Id
    @Column(name = "TEAM_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany
    @JoinColumn(name = "TEAM_ID")
    private List<MemberOneToMany> members = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MemberOneToMany> getMembers() {
        return members;
    }

    public void setMembers(List<MemberOneToMany> members) {
        this.members = members;
    }
}
