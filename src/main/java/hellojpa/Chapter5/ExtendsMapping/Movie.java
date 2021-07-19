package hellojpa.Chapter5.ExtendsMapping;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M")
//DTYPE에 들어갈 값
public class Movie extends Item{
    private String director;
    private String actor;
}
