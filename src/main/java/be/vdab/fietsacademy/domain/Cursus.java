package be.vdab.fietsacademy.domain;


import javax.persistence.*;

@Entity
//@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
@Inheritance( strategy = InheritanceType.JOINED)
@Table( name = "cursussen" )
// discriminator column does not need a member var
//@DiscriminatorColumn( name = "soort" )
public abstract class Cursus {
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private long id;
    private String naam;

    protected  Cursus(){
    }
    public Cursus(String naam) {
        this.naam = naam;
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }
}
