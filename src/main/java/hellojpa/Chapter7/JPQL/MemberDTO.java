package hellojpa.Chapter7.JPQL;

public class MemberDTO {
    private String username;

    public MemberDTO(String username, int age) {
        this.username = username;
        this.age = age;
    }

    private int age;
}
