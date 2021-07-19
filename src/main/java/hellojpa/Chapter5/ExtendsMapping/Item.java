package hellojpa.Chapter5.ExtendsMapping;

/*
    상속매핑 join전략
    @Inheritance(strategy = InheritanceType.JOINED)
    부모테이블의 pk를 가져와서 하위 상속되는 클래스에서 id를 (pk, fk)로 쓰는 전략
    장점은 정규회된 DB설계를 할 수 있다.
    단점은 조인쿼리가 너무많이 나가는 것과 INSERT쿼리가 두번이 나간다 (ITEM, EX) BOOK)

    상속매핑 single_table전략 / 기본 전략 /
    부모테이블에 다 때려박고 하나의 테이블로만 쓴다는 점.
    장점은 편리하다는 장점이 있지만
    단점으로는 용량이 커지면 성능저하로 이어질 뿐 아니라
            null값을 너무 많이 허용해야 한다는 단점이 있습니다.

    상속매핑 table_per
    그냥 테이블을 여러개 만들어버림. 그래서 실효성이 떨어짐.
 */

/**
 * 기본적으로 한테이블에 짱박는 전략. single _table
 * create table Item (
 *        DTYPE varchar(31) not null,
 *         id bigint not null,
 *         name varchar(255),
 *         price integer not null,
 *         artist varchar(255),
 *         author varchar(255),
 *         actor varchar(255),
 *         director varchar(255),
 *         primary key (id)
 */

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
// 부모테이블에서의 구분자인 DTYPE
public class Item {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int price;

}
