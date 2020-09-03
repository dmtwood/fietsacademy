package be.vdab.fietsacademy.domain;
import javax.persistence.*;
import javax.print.Doc;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity @Table( name = "campussen" )
public class Campus {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long id;

    private String naam;

    @Embedded
    private Adres adres;

    @ElementCollection // indicates var with collection of value objects
    @CollectionTable( name = "campussentelefoonnrs", // name of table holding value objects
    joinColumns = @JoinColumn( name = "campusid")) // FK ~ PK campussen > class Campus
    @OrderBy("fax desc") private Set<TelefoonNr> telefoonNrs;


    @OneToMany @JoinColumn( name = "campusId" ) @OrderBy("voornaam, familienaam")
    private Set<Docent> docenten;


    public Campus(String naam, Adres adres) {
        this.naam = naam;
        this.adres = adres;
        this.telefoonNrs = new LinkedHashSet<>();
        this.docenten = new LinkedHashSet<>();
    }
    protected Campus() {
    }

    public Set<Docent> getDocenten() {
        return Collections .unmodifiableSet( docenten );
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public Adres getAdres() {
        return adres;
    }

    public Set<TelefoonNr> getTelefoonNrs() {
        return Collections .unmodifiableSet( telefoonNrs );
    }

    public boolean addTelefoonNr(TelefoonNr telefoonNr) {
        if (! telefoonNrs.contains(telefoonNr) ){
            return telefoonNrs.add(telefoonNr);
        } else return false;
    }

    public boolean removeTelefoonNr(TelefoonNr telefoonNr) {
        return telefoonNrs.remove(telefoonNr);
    }

    public boolean add(Docent docent) {
        var toegevoegd = docenten.add(docent);
        var oudeCampus = docent.getCampus();
        if (oudeCampus != null && oudeCampus != this) {
            oudeCampus.docenten.remove(docent);
        }
        if (this != oudeCampus) {
            docent.setCampus(this);
        }
        return toegevoegd;
    }
}

