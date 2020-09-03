package be.vdab.fietsacademy.domain;

import javax.persistence.*;
import java.time.LocalDate;

//@DiscriminatorValue( "G" )
@Entity
@Inheritance( strategy = InheritanceType.TABLE_PER_CLASS)
@Table( name = "groepscursussen" )
public class GroepsCursus extends Cursus {

    private LocalDate van;
    private LocalDate tot;

    protected GroepsCursus() {
    }
    public GroepsCursus(String naam, LocalDate van, LocalDate tot) {
        super(naam);
        this.van = van;
        this.tot = tot;
    }

    public LocalDate getVan() {
        return van;
    }

    public LocalDate getTot() {
        return tot;
    }
}
