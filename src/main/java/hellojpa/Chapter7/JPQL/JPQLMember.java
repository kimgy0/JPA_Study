package hellojpa.Chapter7.JPQL;

import hellojpa.RoleType;

import javax.persistence.*;

@Entity
public class JPQLMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private int age;

    @Enumerated(EnumType.STRING)
    private RoleType type;

    public RoleType getType() {
        return type;
    }

    public void setType(RoleType type) {
        this.type = type;
    }

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private JPQLTeam team;

    public JPQLTeam getTeam() {
        return team;
    }

    public void setTeam(JPQLTeam team) {
        this.team = team;
    }

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
