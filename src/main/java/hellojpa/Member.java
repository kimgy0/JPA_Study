package hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
/*
    @Entity 라는 것을 적어주게 되면 JPA가 관리하는 것으로 본다.
    name속성이 존재하며 기본적으로 정하지 않았을 때는 클래스 이름을 사용한다.
 */
public class Member {

    @Id
    // 기본키 속성을 매핑한다 -> 이 해당 클래스의 기본키는 id이다.
    private Long id;
    private String name;

    public Member() { }
    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
