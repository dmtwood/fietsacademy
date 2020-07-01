package be.vdab.fietsacademy.domain;


import javax.persistence.*;
import java.util.UUID;

@Entity
//@Table( name = "cursussen" )
//@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
//@Inheritance( strategy = InheritanceType.JOINED)
@Inheritance( strategy = InheritanceType.TABLE_PER_CLASS)
// discriminator column does not need a member var
//@DiscriminatorColumn( name = "soort" )
public abstract class Cursus {
    @Id
//    @GeneratedValue ( strategy = GenerationType.IDENTITY )
//    private long id;
    private String id;
    private String naam;

    protected  Cursus(){
    }
    public Cursus(String naam) {
        this.naam = naam;
        // generate unique UUID - alternative for auto-PK, needed when using Inheritance type table per concrete class
        id = UUID.randomUUID().toString();
    }

//    public long getId() {
//        return id;
//    }
     public String getId() {
        return id;
     }

    public String getNaam() {
        return naam;
    }
}
