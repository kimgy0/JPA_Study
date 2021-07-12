package hellojpa.Chapter1;

import hellojpa.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class jpaEntityContext {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try{
            //영속상태
            Member member = new Member();
            member.setName("영속성 컨텍스트 테스트1");
            member.setId(101L);
            System.out.println("======before======");
            em.persist(member);
            System.out.println("=======after=====");
            Member findMember = em.find(Member.class, 101L);
            System.out.println("findMember.getId() = " + findMember.getId());
            System.out.println("findMember.getName() = " + findMember.getName());
            /**
             * ======before======
             * 02:41:03.049 [main] DEBUG org.hibernate.event.internal.AbstractSaveEventListener - Generated identifier: 101, using strategy: org.hibernate.id.Assigned
             * =======after=====
             * findMember.getId() = 101
             * findMember.getName() = 영속성 컨텍스트 테스트1
             *
             * em.persist(member)를 해주고
             * em.find로 101L을 기본키로 하고 있던 객체를 찾아왔는데
             * select쿼리가 안나갔다는 것은 영속성 컨텍스트에 이미 존재하기 때문에 db까지 쿼리를 쏠 필요가 없이
             * 영속성 컨텍스트 내부에 1차 캐시에서 가져왔다는 것이다.
             * 때문에 영속성 컨텍스트는 중간에서 중요한 역할을 할 수 있다고 한다.
             */

        }catch (Exception e){
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        emf.close();

    }
}
