package hellojpa.Chapter5.ExtendsMapping;
/*
    모든 db에 들어갈 공통 컬럼이 존재하면 이런식으로
    @MappedSuperClass로 삽입할 수 있다.
 */

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {
    @Column(name = "CREATE_TIME") // 어노테이션도 달아줄 수 있습니다.
    //AttributeOverrides(AttributeOverride(name = "id(변수이름)", column=@column(name = "재정의할 이름."))

    private String createBy;
    private LocalDateTime createDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;
    /**
     * getter setter 다 만들어주고
     * extends 로 엔티티 어노테이션이 달린 클래스들에 상속해주면
     * 공통적으로 이들의 속성이 들어가게 된다.
     *
     * class로 쓰지말고 될 수 있으면 abstract로 삽입하자.
     */
}
