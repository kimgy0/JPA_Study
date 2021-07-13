package hellojpa.Chapter2;


import javax.persistence.*;


/***********************************
 * *********************************
 * *********************************
 * ************기본키 전략************
 * *********************************
 * *********************************
 * *********************************
 * *********************************
 **********************************/

@Entity
@TableGenerator(name = "MEMBER_SEQ_GENERATOR",
                table = "MY_SEQUENCES",
                pkColumnName = "MEMBER_SEQ", allocationSize = 1)


public class MemberMapping {

    @Id
    // 직접 기본키 값을 셋팅을 해줍니다.
    // 데이터베이스에 setId() 로 먼저 세팅 후에 db에 저장합니다.
    /*
        1.  @GeneratedValue(strategy = GenerationType.IDENTITY)
            mysql로 따지면 auto_increment 속성을 넣은것과 비슷함.
            db에 쿼리를 날리면서 기본키 값을 정합니다. (db에 기본키 생성을 위임함) id값을 세팅하지않고 null 상태에서 db에 넘김

            일단 db에 insert쿼리를 날립니다. 그리고 영속성 컨텍스트에 저장합니다.
            그래서 계속 flush? 된다고 생각하면 됩니다.
            ==================================================================================================

        2. @GeneratedValue(strategy = GenerationType.SEQUENCE)
           시퀀스 오브젝트를 db에 생성해준 후에 그 오브젝트로 할당을 합니다.
           * 시퀀스 전략은 em.persist() 호출 후에 db에 쿼리를 날려 시퀀스에서 기본키 값을 가져와서 영속성에 저장하고
           * 커밋이나 플러시 시점에 쿼리를 날려줍니다.

           2-1. 만약 공통 시퀀스 (하이버네이트에서 기본으로 만들어주는 시퀀스 말고 시퀀스를 따로 관리하고 싶다면)
           ! 클래스 단에 @SequenceGenerator( name = "시퀀스 이름", squenceName = " 매핑할 데이터베이스 시퀀스 이름 "
                                            initialValue = 1, allocationSize = 1)
           ! @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = " 시퀀스 이름 ")

           일단 시퀀스에서 값을 하나 가져오고 그 값에 대해 영속성컨텍스트에 저장한 후 지연sql에 쿼리를 쌓아두다가
           flush 나 commit 시점에 db에 쿼리를 날립니다.

           **********  allocationValue=50
           **********  계속 값을 불러오면 성능이슈가 생기기 마련 그래서 한번에 50개를 불러오고 메모리에 쌓아둠.
           **********  그리고 기본값이 필요할 때 마다 db에 접근을 50 까지 굳이 안해도 기본키가 자동으로 setting 됨.
            ==================================================================================================
        3. 테이블 매핑 전략

            @TableGenerator(name = "MEMBER_SEQ_GENERATOR",
                    table = "MY_SEQUENCES",
                    pkColumnName = "MEMBER_SEQ", allocationSize = 1)

            @GeneratedValue(strategy = GenerationType.TABLE
                        ,generator = "MEMBER_SEQ_GENERATOR")
            =================================================================
            이렇게 되면 아래와 같은 테이블을 하나 만들어서 기본키 값을 매핑합니다.

            create table MY_SEQUENCES (
             MEMBER_SEQ varchar(255) not null,
                next_val bigint,
             primary key (MEMBER_SEQ)
    )
     */
    @GeneratedValue(strategy = GenerationType.TABLE
                    ,generator = "MEMBER_SEQ_GENERATOR")
    private Long id;

    @Column(name = " name", nullable = false)
    private String username;

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

    public MemberMapping() {
    }
}
