package be.vdab.fietsacademy.domain;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

//@DiscriminatorValue( "I" )
@Entity
@Inheritance( strategy = InheritanceType.TABLE_PER_CLASS)
@Table( name = "individuelecursussen" )
public class IndividueleCursus extends Cursus {

    private int duurtijd;

    protected IndividueleCursus(){
    }

    public IndividueleCursus(String naam, int duurtijd) {
        super(naam);
        this.duurtijd = duurtijd;
    }

    public int getDuurtijd() {
        return duurtijd;
    }

}


