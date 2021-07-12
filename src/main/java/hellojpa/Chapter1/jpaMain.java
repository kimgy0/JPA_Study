package hellojpa.Chapter1;

import hellojpa.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class jpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        //고객이 실행하는 하나의 단위를 수행할 때 마다 em을 생성해주어야 한다.
        //em은 스레드간에 절대 공유를 하면 안된다. (사용하고 버려줘야함.) 동시성문제 때문에 오류가 날 수 있음.

        EntityTransaction tx = em.getTransaction();

        /*
            트랜잭션 내부에서 저장해주고 수정 삭제등 작업이 이루어져야한다.
            tx.begin()
            tx.commit() // flush and commit
         */
        tx.begin();
        try {
            /**  저장  **/
//            Member member = new Member();
//            member.setId(1L);
//            member.setName("HelloA");
//            em.persist(member); // 저장
            /**  찾기  **/
            Member member = em.find(Member.class, 1L);
            System.out.println("member.getId() = " + member.getId());
            System.out.println("member.getName() = " + member.getName());

            /**  수정  **/
            member.setName("HelloJPA");
            //em.persist(member); 가 필요가 없다.
            //JPA가 트랜잭션을 커밋하는 시점에 변경을 감지한다.

            /**  조건에 따른 검색(JPQL)  **/
            List<Member> result = em.createQuery("select m from Member m", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)//jpql을 쓰는 이유는 페이징이 잘돼서 ! -> 1번부터 10개 가져와!
                    .getResultList();
            //jpql에서는 절대 테이블을 대상으로 쿼리를 날리지 않기 때문에 대상을 객체로 두고 쿼리를 날려야 합니다.
            for (Member member1 : result) {
                System.out.println("member1 = " + member1);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

        /*
            <persistence-unit name="hello"> //persistence.xml에 unitName으로 설정해준 값을 String 형식으로 인자를 넣어줍니다.
            관례로 emf라고 많이 써주기도 합니다.
         */
    }
}
