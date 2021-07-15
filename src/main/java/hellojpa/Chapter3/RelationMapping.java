package hellojpa.Chapter3;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class RelationMapping {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try{
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            MemberRelationMapping member = new MemberRelationMapping();
            member.setUsername("member1");
            member.setTeam(team);
            em.persist(member);

//            team.getMembers().add(member);
            /*
                해줘야하는 이유는 없어도된다. 하지만 em.flush와 em.clear가 호출이 되는 순간
                자동으로 반대편의 team의 members에 자동으로 객체를 넣어준다.
                하지만 em.flush()가 없어졌을 경우 team 자체는 영속성컨텍스트의 1차캐시에만 존재한다.
                말그대로 팀객체 상태 그대로 영속성컨텍스트에 저장된것이다.

                그래서 나중에 이런 양방향조회가 없어지는 경우를 생각하면 getmembers 에 add를 따로 쳐줘야되는게 맞다.
             */
            em.flush();
            em.clear();

            Team findTeam = em.find(Team.class, team.getId());
            List<MemberRelationMapping> members = findTeam.getMembers();

            for (MemberRelationMapping memberRelationMapping : members) {
                System.out.println("memberRelationMapping = " + memberRelationMapping);
            }


            em.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            em.getTransaction().rollback();
        }finally{
            em.close();
        }
        emf.close();
    }
}
