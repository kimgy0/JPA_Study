package hellojpa.Chapter7.JPQL;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;

public class JPQLMain2 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try{
            JPQLTeam teamA =new JPQLTeam();
            teamA.setName("팀A");
            em.persist(teamA);

            JPQLTeam teamB =new JPQLTeam();
            teamB.setName("팀B");
            em.persist(teamB);

            JPQLMember member1 = new JPQLMember();
            member1.setUsername("관리자1");
            member1.setTeam(teamA);
            em.persist(member1);

            JPQLMember member2 = new JPQLMember();
            member2.setUsername("관리자2");
            member2.setTeam(teamA);
            em.persist(member2);

            JPQLMember member3 = new JPQLMember();
            member3.setUsername("관리자3");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            String query = "select m.team From JPQLMember m";
            //묵시적 조인 발생 ( 단일값 연관 경로 ) inner조인 발생
            String query1 = "select t.members From JPQLTeam as t";
            //컬렉션으로 하면 탐색도 하지 못할뿐더러 묵시적인 내부조인이 발생한다.
            //컬렉션 자체적으로 t.members.size 정도는 가능하나 다른 경로표현으로 탐색은 하지 못함.

            String query1as2 = "select m.username From JPQLTeam as t join t.members as m";
            //이렇게 명시적조인으로 from절에서 별칭 m을 주게 되면 다시 시작할 수 있음. 경로표현가능능
           List<JPQLTeam> resultList = em.createQuery(query, JPQLTeam.class).getResultList();
            Collection resultList1 = em.createQuery(query1, Collection.class).getResultList();

            /**************** fetch join ****************/
            /******** many to one 관계의 fetch join **********/

            String fetchQuery = "select m From JPQLMember m";
            List<JPQLMember> rs = em.createQuery(fetchQuery, JPQLMember.class).getResultList();
            for (JPQLMember r : rs) {
                System.out.println("r.getUsername() = " + r.getUsername());
                System.out.println("r.getTeam().getName() = " + r.getTeam().getName());
                //관리자1, 팀A(SQL)
                //관리자2, 팀A(영속성컨텍스트 1차캐시)
                //관리자3, 팀B(SQL)
                // => TEAM자체가 지연로딩으로 설정이 되어있어서 일단 멤버쿼리만나가고
                // 다음으로 TEAM을 쓸 필요가 있을 때 가지고오는 쿼리를 날립니다.
                /*
                 * 만약에 회원이 백명이면 쿼리가 백번나감 N+1번 문제가 발생하게 됨.
                 * ** 해결 방법 **
                 * String fetchQuery =
                 *  "select m From JPQLMember m JOIN FETCH m.team";
                 *
                 * 프록시가 아닌 실제 엔티티를 조인해서 다들고옴.
                 */
            }
            /******** 일대다 컬렉션의 fetch join **********/
            String fetchQuery1 = "select t From JPQLTeam t JOIN FETCH t.members";
            List<JPQLTeam> resultList2 = em.createQuery(fetchQuery1, JPQLTeam.class).getResultList();
            for (JPQLTeam jpqlTeam : resultList2) {
                System.out.println("jpqlTeam.getName() = " + jpqlTeam.getName());
                System.out.println("jpqlTeam.getMembers().size() = " + jpqlTeam.getMembers().size());
                // 팀 a + 2
                // 팀 a + 2
                // 팀 b + 1
                //일대다는 조인이 되면 항상 뻥튀기가 됨.
                //하지만 각 팀 객체는 같은 주소의 컬렉션을 가리키고 있음.
                //객체지향 스펙상 어쩔 수 없지만
                //중복을 없애주고 싶으면 select 뒤에 distinct를 붙여준다.

                /*
                 * 둘이상의 컬렉션은 페치조인 하지마세요
                 * 페치조인 대상에 별칭을 주지마세요
                 * 페치조인의 컬렉션은 페이징하지마세요.
                 */



            }

            /*******  fetch join의 한계 **********/
            //페치조인의 대상에는 as 별칭을 주면 안된다.

            /******* name쿼리 ********/
            //클래스에 NAMEDQUERY(NAME = QUERY =  ) 를 설정해주면
            //어마어마한 메리트 => 어플리케이션 로딩시점에 파싱해서 캐시에 올려놓음
            //로딩시간이 없는 것이 장점.
            //성능상 이점이 어마어마함.

            // 쿼리에는 EM.CREATEQUERY (클래스이름 . 네임드쿼리이름. ,OOO.CLASS)
            //성능이 오짐짐

            //xml에 정의하는 방법도 존재하는데 그냥 알아만둬라!

            em.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
