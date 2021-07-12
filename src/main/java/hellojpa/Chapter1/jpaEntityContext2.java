package hellojpa.Chapter1;

import hellojpa.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class jpaEntityContext2 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try{
            Member findMember = em.find(Member.class, 101L);
            System.out.println("===========첫번째 쿼리===========");
            Member findMember2 = em.find(Member.class, 101L);
            System.out.println("===========두번째 쿼리===========");
            em.getTransaction().commit();

            /**
             * 그래서 새프로젝트를 시작했을 떄 em.find로 아무것도 없는 영속성 컨텍스트에 1차캐시로 객체를 select쿼리로 불러오고
             * 1차 캐시에 있는 상태부터 두번째 em.find를 호출했을 때는 쿼리를 날리지 않고 가져옵니다.
             *
             * 사실 em 자체가 트랜잭션이 한 번 오갈때 마다 수행되는 단위라서 현업에서는 1차캐시의 이런 면모는 별로 중요하지 않다.
             *
             *
             * ===========첫번째 쿼리===========
             * 02:49:04.626 [main] DEBUG org.hibernate.SQL -
             *     select
             *         member0_.id as id1_0_0_,
             *         member0_.name as name2_0_0_
             *     from
             *         Member member0_
             *     where
             *         member0_.id=?
             * Hibernate:
             *     select
             *         member0_.id as id1_0_0_,
             *         member0_.name as name2_0_0_
             *     from
             *         Member member0_
             *     where
             *         member0_.id=?
             * 02:49:04.628 [main] DEBUG org.hibernate.resource.jdbc.internal.ResourceRegistryStandardImpl - HHH000387: ResultSet's statement was not registered
             * 02:49:04.629 [main] DEBUG org.hibernate.loader.entity.plan.AbstractLoadPlanBasedEntityLoader - Done entity load : hellojpa.Member#101
             * ===========두번째 쿼리===========
             *
             */
        }catch (Exception e){
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        emf.close();

    }
}
