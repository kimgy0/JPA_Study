package hellojpa.Chapter7.JPQL;

import hellojpa.Member;

import javax.persistence.*;
import java.util.List;

public class JPQLMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try{
            JPQLMember member = new JPQLMember();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            TypedQuery<JPQLMember> query = em.createQuery("select m from JPQLMember m", JPQLMember.class);
            //반환 타입이 명확할 때 TypedQuery를 사용합니다.
            List<JPQLMember> resultList = query.getResultList();
            //결과를 여러개 출력할 때, 결과가 없으면 빈 리스트를 반환해준다.

            /*파라미터 바인딩*/
            TypedQuery<JPQLMember> query2 = em.createQuery("select m from JPQLMember m where m.username= :username", JPQLMember.class);
            //파라미터 바인딩은
            query2.setParameter("username", "member1");

            // JPQLMember singleResult = query.getSingleResult();
            // 결과가 정확하게 하나만 나와야 합니다 - > 아니면 예외가 터져버립니다.

            for (JPQLMember jpqlMember : resultList) { }
            Query query1 = em.createQuery("select m.username, m.age from JPQLMember m");
            //반환 타입이 명확하지 않을 떄는 Query타입을 사용합니다.
            em.flush();
            /*
               1. 엔티티와 속성은 대소문자를 구분합니다.
               2. 별칭은 필수로하며 엔티티 이름을 사용 (클래스 기본 이름을 엔티티이름으로 디폴트 설정)
             */

            /*************************** 프로젝션 ********************************/

            // 나머지는 다 대상프로젝션이 가능하지만 하나 궁금한것.
            List resultList1 = em.createQuery("select m.username , m.age from JPQLMember m ").getResultList();
            for (Object o : resultList1) {
                Object[] result = (Object[]) o; // 리스트[[username][age],[][],.... ]
                //명기되지 않은 타입은 배열로 타입캐스팅을 해주어야한다.
                System.out.println("result = " + result[0]);
                System.out.println("result = " + result[1]);
            }
            //프로젝션에 대해서 순수 dto 로 매핑하고 싶을 떄 (vo)
            List<MemberDTO> resultList2 =
                    em.createQuery("select new hellojpa.Chapter7.JPQL.MemberDTO(m.username , m.age) from JPQLMember m ",MemberDTO.class)
                    .getResultList();
            /******************************************************************/
            /*************************** 페이징 ********************************/
            List<JPQLMember> resultList3 = em.createQuery("select m from Member m order by m.age desc", JPQLMember.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();
            for (JPQLMember jpqlMember : resultList3) { }

            /******************************************************************/
            /*************************** 조인 ********************************/

            String innerquery = "select m from Member m inner join m.team t where t.name : teamName";
            String leftouterquery = "select m from Member m left [outer] join m.team t where t.name : teamName";
            String settaquery = "select m from Member m, Team t where m.username = :username";
            //setta조인은 n x m 의 테이블끼리의 카디널리티가 서로 곱해져서 막조인이 되는 현상 --
            /*
            1. 공통적으로 join - - on- - 절도 적어줄 수 있음.
            2. 연관관계 없는 엔티티를 외부조인 할 수 있다.
               - 회원의 이름과 팀의 이름이 같은 대상 외부 조인.
               select m , t from Member m left join m.team t on t.teamname = m.username;
               연관관계가 아예 관계 없는 아이끼리 해주려면 on 절에 적어주게 되면 조인 조건으로 걸려들어갑니다.
             */

            /******************************************************************/
            /*************************** 서브쿼리 ********************************/
            //메인쿼리와 서브쿼리의 from 객체는 m2 , m1으로 나뉘어야함 -> 성능면에서 좋음.
            //from절에 서브쿼리를 적용하지 못함.
            //where이나 having 절에서만 서브쿼리를 사용할 수 있음.

            /****************************************************************/
            /****************************** 값타입 **************************/
            String valueQuery = "select m.username, 'HELLO', TRUE from JPQLMember m" +
                    " where m.type = hellojpa.RoleType.ADMIN";
            List<Object[]> resultList4 = em.createQuery(valueQuery).getResultList();
            for (Object[] objects : resultList4) {
                System.out.println("objects[0] = " + objects[0]);
                System.out.println("objects[1] = " + objects[1]);
                System.out.println("objects[2] = " + objects[2]);
            }
            //enum타입 같은경우에는 패키지명을 포함해서 다 적어준다.
            //추가로 type()이라는 쿼리함수를 쓸 때는 이렇게한다
            /* select i from Item i where type(i) = Book ,ITEM.CLASS*/

            /****************************************************************/
            /****************************** 조건식 CASE 등 **************************/
            String caseQuery = "select "+
                    "case when m.age <= 10 then '학생요금' " +
                    "     when m.age >= 60 then '경로요금' " +
                    "     else '일반 요금' " +
                    "     end " +
                    "     from JPQLMember m";
            List<String> resultList5 = em.createQuery(caseQuery, String.class).getResultList();
            for (String s : resultList5) {
                System.out.println("s = " + s);
            }

            /*1*/
            String coalesce = "select coalesce(m.username, '이름 없는 회원') from Member m";
            //만약에 username을 읽다가 null로 되어있는 값이 있으면 이름없는 회원이라는 스트링을 반환합니다.
            String nullifquery = "select nullif(m.username, '관리자') from Member m";
            //이름이 null로 되어있으면 관리자를 반환합니다.



            /****************************************************************/
            /****************************** 기본 함수 **************************/

             /*
              * concat ('a' , 'b')
              * substring (m.username, 2, 3)
              * locate ('de','abcdegf') +> return 타입 integer
              * 그 외 함수들은 책에 ㄱㄱ
              *
              * select size(t.members) from team t ; =>컬렉션의 크기를 띄워줌.
              * select index (t.members) from team t ; =>컬렉션의 크기를 띄워줌.
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
