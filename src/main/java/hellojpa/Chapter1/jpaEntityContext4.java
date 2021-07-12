package hellojpa.Chapter1;

import hellojpa.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class jpaEntityContext4 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try{
            Member member = em.find(Member.class, 150L);

            member.setName("변경된 이름");
            System.out.println("==========================");
            em.getTransaction().commit();
            System.out.println("==========================");
            /**
             * 또 이 두줄 사이에 쿼리가 나가게 되는데
             * jpa는 변경감지라는 기능이 있다. 그래서 1차캐시에 find로 불러온 객체가
             * 내부적으로 어떤 정보가 바뀌었을 경우 기존의 1차캐시에 올라왔던 객체와
             * 내가 바꾼 객체를 flush()가 날라오는 시점에 비교를 하게 된다.
             *
             * 그렇게 비교를 하여 바뀌었을 경우 지연sql 모음에 update쿼리를 던져서 모아두다가
             * 나중에 실 db에 commit하기 전에 이 쿼리를 날립니다.
             *
             *            -스냅샷의 개념.
             */
        }catch (Exception e){
            e.printStackTrace();
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
