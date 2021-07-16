package hellojpa.Chapter4.OneToOne;

import javax.persistence.*;

@Entity
public class Locker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCKER_ID")
    private Long id;

    @Column(name = "LOCKERNAME")
    private String name;

    @OneToOne(mappedBy = "locker")
    private MemberOneToOne member;
}
