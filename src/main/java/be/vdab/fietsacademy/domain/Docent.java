package be.vdab.fietsacademy.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "docenten")
// best practice -> all named queries in src/main/resources/META-INF/orm.xml
//@NamedQuery( name = "Docent.FindByWeddeBetween",
//        query = "select d from Docent d where d.wedde between :van and :tot" +
//                " order by d.wedde, d.id")
public class Docent {
    // MEMBER VARS
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Id private long id;
    private String voornaam;
    private String familienaam;
    private BigDecimal wedde;
    private String emailAdres;
    @Enumerated(EnumType.STRING)
    private Geslacht geslacht;

    //     @ElementCollection >> collection of value objects
    @ElementCollection
    @CollectionTable( name = "docentenbijnamen",
    // foreign key referring to primary column of Docenten-tabel ~ entity class Docent
    joinColumns = @JoinColumn( name = "docentid"))
    // @Column > column where value objects are stored
    @Column( name = "bijnaam")
    private Set<String> bijnamen;

    @ManyToOne( optional = false )
    @JoinColumn( name = "campusid" )
    private Campus campus;

    // CONSTRUCTORS
    protected Docent() {
    }

    public Docent(String voornaam, String familienaam, BigDecimal wedde, String emailAdres, Geslacht geslacht, Campus campus) {
        this.voornaam = voornaam;
        this.familienaam = familienaam;
        this.wedde = wedde;
        this.emailAdres = emailAdres;
        this.geslacht = geslacht;
        this.bijnamen = new LinkedHashSet<>();
        setCampus( campus );
    }

    // GET & SET
    public long getId() {
        return id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getFamilienaam() {
        return familienaam;
    }

    public BigDecimal getWedde() {
        return wedde;
    }

    public String getEmailAdres() {
        return emailAdres;
    }

    public Geslacht getGeslacht() {
        return geslacht;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    // METHODS
    public void opslag(BigDecimal percentage){
//        throw new UnsupportedOperationException();
        if ( percentage .compareTo( BigDecimal.ZERO ) <= 0 ){
            throw new IllegalArgumentException();
        }
        var factor = BigDecimal.ONE .add(
                percentage .divide( BigDecimal.valueOf( 100 )  )
        );
        wedde = wedde .multiply(
                factor,
                new MathContext( 2, RoundingMode.HALF_UP)
        );
    }

    public Set<String> getBijnamen(){
        return Collections.unmodifiableSet( bijnamen );
    }
    // use boolean Datatype for methods to add & remove
    public boolean addBijnaam(String bijnaam){
        if ( bijnaam.trim().isEmpty() ) { throw new IllegalArgumentException(); }
        return bijnamen.add( bijnaam );
    }

    public boolean removeBijnaam(String bijnaam){
        return bijnamen.remove( bijnaam );
    }
}





