package hellojpa;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/***********************************
 * *********************************
 * *********************************
 * *******기본 어노테이션 속성**********
 * *********************************
 * *********************************
 * *********************************
 * *********************************
 **********************************/


@Entity
/*
    @Entity 라는 것을 적어주게 되면 JPA가 관리하는 것으로 본다.
    name속성이 존재하며 기본적으로 정하지 않았을 때는 클래스 이름을 사용한다.
 */
public class Member {

    @Id
    // 기본키 속성을 매핑한다 -> 이 해당 클래스의 기본키는 id이다.
    private Long id;
    @Column(name = "name")
    //@Column(unique = true, length = 10, nullable = false)
    // unique     -> true 유니크 제약조건
    // insertable -> 삽입 가능 여부   true | false
    // updatable  -> 변경 가능 여부   true | false
    // name       -> 매핑시킬 db의 컬럼이름
    // nullable   -> not null 제약조건 true | false
    // columnDefinition -> " varchar(200) not null primary key "
    private String name;

    @Column (precision = 10 , scale = 2)
    // precision 은 소수점자리를 포함한 전체 자리 개수
    // scale 은 소수점 자리의 개수
    // bigdecimal 이고 숫자가 존나 클 때 적용 가능한 어노테이션 속성
    private BigDecimal age;

    @Enumerated(EnumType.STRING)
    // ORDINAL 은 순서를 숫자로 저장하니까 나중에 ENUM이 수정되면 알아보기가 어려움.
    // EnumType . ORDINAL 은 ENUM 순서를 데이터베이스에 저장
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    /*
        DATE, 날짜
        TIME, 시간
        TIMESTAMP; 날짜+시간
        Date객체에는 날짜 시간이 있지만 db에서는 날짜 시간을 나눠서 할당한다.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description;
    // VARCHAR를 넘어서는 큰 범위.

    @Transient
    // 그냥 메모리에만 임시로 데이터를 사용하고 싶을 때, db에는 필요가 없을 때 쓰는 어노테이션
    private int temp;

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

    public BigDecimal getAge() {
        return age;
    }

    public void setAge(BigDecimal age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public Member() { }
    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
