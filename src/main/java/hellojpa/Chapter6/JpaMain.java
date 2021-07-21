package hellojpa.Chapter6;

import hellojpa.Chapter3.Team;
import hellojpa.Member;

import javax.persistence.*;
import java.lang.reflect.Proxy;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try{
            TeamProxy team = new TeamProxy();
            team.setTeamName("hello team");
            em.persist(team);

            MemberProxy member = new MemberProxy();
            member.setUsername("hello");
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            MemberProxy memberp = em.find(MemberProxy.class, member.getId());
            System.out.println("memberp.getTeam().getClass() = " + memberp.getTeam().getClass());
            //이거도 밑에 fetch로 인해서 team은 프록시로 끄집어나옴
            //@ManyToOne(fetch = FetchType.LAZY)
            System.out.println("==================");
            System.out.println("프록시 초기화 팀" + memberp.getTeam().getTeamName());
            System.out.println("==================");
            /**
             * memberp.getTeam().getClass() = class hellojpa.Chapter6.TeamProxy$HibernateProxy$qJM6cC7F
             * 프록시 인것을 확인하고 밑의 프록시의 team의 필드 아무거나 하나 건드리는 순간!
             * ==================
             *     select
             *         teamproxy0_.TEAM_ID as TEAM_ID1_13_0_,
             *         teamproxy0_.teamName as teamName2_13_0_
             *     from
             *         TeamProxy teamproxy0_
             *     where
             *         teamproxy0_.TEAM_ID=?
             * 프록시 초기화 팀hello team
             * ==================
             */

            //팀과 멤버 다 조인시켜서 가져오기.
            /*
                select
                    memberprox0_.MEMBER_ID as MEMBER_I1_8_0_,
                    memberprox0_.TEAM_ID as TEAM_ID3_8_0_,
                    memberprox0_.username as username2_8_0_,
                    teamproxy1_.TEAM_ID as TEAM_ID1_13_1_,
                    teamproxy1_.teamName as teamName2_13_1_ 
                from
                    MemberProxy memberprox0_ 
                left outer join
                    TeamProxy teamproxy1_ 
                        on memberprox0_.TEAM_ID=teamproxy1_.TEAM_ID 
                where
                    memberprox0_.MEMBER_ID=?

                    findMember.getUsername() = hello
                    findMember.getId() = 1
             */
            MemberProxy findMember = em.find(MemberProxy.class, member.getId());
            System.out.println("findMember.getUsername() = " + findMember.getUsername());
            System.out.println("findMember.getId() = " + findMember.getId());

            em.clear(); // 영속성 컨텍스트에 뭔가 존재하면 프록시는 무용지물
            // getReferenceMember.getId() = 1
            //getReferenceMember.getUsername() = hello 이런식으로 바로 위 객체를 그냥 가져옴

            //프록시 객체를 이용해서 불러오기.
            MemberProxy getReferenceMember = em.getReference(MemberProxy.class, member.getId());
            System.out.println("getReferenceMember.getId() = " + getReferenceMember.getId());
            System.out.println("getReferenceMember = "+ getReferenceMember.getClass());
            //getReferenceMember = class hellojpa.Chapter6.MemberProxy$HibernateProxy$XwUjU9u7
            System.out.println("getReferenceMember.getUsername() = " + getReferenceMember.getUsername());
            /*
                실제 객체를 쓸 떄 조회하는 기능을 해줍니다.
                실제 객체를 사용할 때 쿼리를 날린다는 뜻.

                실제 getreference각 반환하는 객체는 memberproxy를 상속받고 있는, 메서드를 참조하고 있는 가짜클래스인
                프록시 객체를 꺼내서 (걍 껍데기) 저자리에 끼워넣습니다.

                그래서 프록시 객체를 쓸 때는 항상 타입체크를 잘해야만 합니다.
                getReferenceMember = class hellojpa.Chapter6.MemberProxy$HibernateProxy$XwUjU9u7
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
