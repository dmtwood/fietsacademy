package be.vdab.fietsacademy.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Entity
@Table(name = "docenten")
public class Docent {
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Id private long id;
    private String voornaam;
    private String familienaam;
    private BigDecimal wedde;
    private String emailAdres;
    @Enumerated(EnumType.STRING)
    private Geslacht geslacht;

    protected Docent() {
    }

    public Docent(String voornaam, String familienaam, BigDecimal wedde, String emailAdres, Geslacht geslacht) {
        this.voornaam = voornaam;
        this.familienaam = familienaam;
        this.wedde = wedde;
        this.emailAdres = emailAdres;
        this.geslacht = geslacht;
    }

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
}





