package hellojpa.Chapter5;

import hellojpa.Chapter4.OneToMany.MemberOneToMany;
import hellojpa.Chapter4.OneToMany.TeamOneToMany;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class jpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try{


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
