package hellojpa.Chapter4;

import hellojpa.Chapter4.OneToMany.MemberOneToMany;
import hellojpa.Chapter4.OneToMany.TeamOneToMany;
import hellojpa.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class jpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try{

            MemberOneToMany member = new MemberOneToMany();
            member.setName("member1");
            em.persist(member);

            TeamOneToMany team = new TeamOneToMany();
            team.setName("team1");


            team.getMembers().add(member);
            //이 부분에서 update쿼리가 한번 더 나감.
            //멤버에 있는 팀 id 가 null로 저장이 된 상태

            em.persist(team);
            /*
            1. 일대다 단방향
            항상 다 쪽에 외래키가 존재하다 보니까 연관관계의 주인은 팀인데
            외래키가 멤버에 있어서 team 의 list에 add 하는 순간 update
            member로 넘어가서 update를 쳐주는 상태가 되는 것임.

            객체적으로 손해를 좀 더 보더라도 멤버에 외래키를 놓고 주인을 멤버로
            만드는 것이 현명한 선택.

            create one-to-many row hellojpa.Chapter4.OneToMany.TeamOneToMany.members
                    update MemberOneToMany set TEAM_ID=? where MEMBER_ID=?

            2. 일대다 양방향
            @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)

            팀이 주인인 상태에서는 어쩔 수 없이 mappedby가 없다.
            연관관계의 주인이 아닌 아이는 뭔가를 바꿔주지 못하도록
            삽입 수정을 따로 못하게 설정하는 joincolumn의 속성을 사용하도록 한다.

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
