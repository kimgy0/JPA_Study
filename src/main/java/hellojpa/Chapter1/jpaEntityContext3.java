package hellojpa.Chapter1;

import hellojpa.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class jpaEntityContext3 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try{
            //영속성 컨텍스트의 1차 캐시 검증.
            Member member1 = new Member(150L,"A");
            Member member2 = new Member(160L,"B");
            em.persist(member1);
            em.persist(member2);
            System.out.println("=============================");
            System.out.println("이 밑에서부터 쿼리가 나갑니다. commit되는 순간.");
            /**
             *
             *=============================
             * 이 밑에서부터 쿼리가 나갑니다. commit되는 순간.
             * 인서트 쿼리 2개가 나간다.
             *
             *
             *  03:02:20.212 [main] DEBUG org.hibernate.SQL -
             *      insert hellojpa.Member
             *         insert
                    * into
                    * Member
                    * (name, id)
             *values
                    * ( ?, ?)
             *Hibernate:
             *      insert hellojpa.Member
             *
            insert
                    * into
                    * Member
                    * (name, id)
             *values
                    * ( ?, ?)
             *03:02:20.229[main] DEBUG org.hibernate.SQL -
             *      insert hellojpa.Member
             *
            insert
                    * into
                    * Member
                    * (name, id)
             *values
                    * ( ?, ?)
             *Hibernate:
             *      insert hellojpa.Member
             *
            insert
                    * into
                    * Member
                    * (name, id)
             *values
                    * ( ?, ?)
             *
             */

            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
