package hellojpa.Chapter2;

import hellojpa.Member;
import hellojpa.RoleType;

import javax.management.relation.Role;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/***********************************
 * *********************************
 * *********************************
 * *******자동테이블 생성방법**********
 * *********************************
 * *********************************
 * *********************************
 * *********************************
 **********************************/


public class jpaEntityMapping {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        /*  PERSISTENCE.XML
         *
         *  <property name="hibernate.hbm2ddl.auto" value="create" />
         *  <********** drop-create , validate , update , none ... 등등이 있다. **********>
         *   drop table Member if exists
         *
         *    create table Member (
         *        id bigint not null,
         *         age integer,
         *         createDate timestamp,
         *         description clob,
         *         lastModifiedDate timestamp,
         *         name varchar(255),
         *         roleType varchar(255),
         *         primary key (id)
         *     )
         */
        try{

            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
        }finally{
            em.close();
        }
        emf.close();
    }
}
