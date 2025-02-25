package be.vdab.fietsacademy.domain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.*;

class DocentTest {
    private static final BigDecimal WEDDE = BigDecimal.valueOf(2000);
    private Docent docent1, docent2, nogEensDocent1;
    private Campus campus1, campus2;

    @BeforeEach
    void beforeEach() {
        campus1 = new Campus( "test", new Adres("test","test","test","test") );
        campus2 = new Campus("test2", new Adres("test2", "test2", "test2", "test2"));
        docent1 = new Docent("test", "test", WEDDE, "test@test.be", Geslacht.MAN

                // commented for one to many, p. 62
                , campus1
                 );
        docent2 = new Docent("test2", "test2", WEDDE, "test2@test.be", Geslacht.MAN, campus1);
        nogEensDocent1 = new Docent("test", "test", WEDDE, "test@test.be", Geslacht.MAN, campus1);
    }
    @Test
    void opslag() {
        docent1.opslag(BigDecimal.TEN);
        assertThat(
                docent1.getWedde()
        ).isEqualByComparingTo("2200");
    }
    @Test
    void nullOpslagMislukt() {
        assertThatNullPointerException().isThrownBy(
                () -> docent1.opslag(null)
        );
    }
    @Test
    void opslag0Mislukt() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> docent1.opslag(BigDecimal.ZERO)
        );
    }

    @Test
    void negatieveOpslagMislukt() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> docent1.opslag(BigDecimal.valueOf(-1))
        );
    }

    @Test
    void eenNieuweDocentHeeftGeenBijnamen() {
        assertThat( docent1.getBijnamen() ).isEmpty();
    }

    @Test
    void bijnaamToevoegen() {
        // addBijnaam has a boolean datatype
        assertThat( docent1.addBijnaam("test") ).isTrue();
        assertThat( docent1.getBijnamen() ).containsOnly("test");
    }

    @Test
    void tweeDezelfdeBijnamen() {
        docent1.addBijnaam("test");
        assertThat( docent1.addBijnaam("test") ).isFalse();
        assertThat( docent1.getBijnamen() ).containsOnly("test");
    }

    @Test
    void nullAlsBijnaam() {
        assertThatNullPointerException().isThrownBy(
                () -> docent1.addBijnaam( null )
        );
    }

    @Test
    void legeBijnaam() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> docent1.addBijnaam( "" )
        );
    }

    @Test
    void enkelSpatiesAlsBijnaam() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> docent1.addBijnaam( "   " )
        );
    }

    @Test
    void bijnaamVerwijderen() {
        docent1.addBijnaam( "test" );
        assertThat( docent1.removeBijnaam( "test" ) ).isTrue();
        assertThat( docent1.getBijnamen() ).isEmpty();
    }

    @Test
    void onbestaandeBijnaamVerwijderen() {
        docent1.addBijnaam( "test" );
        assertThat( docent1.removeBijnaam( "test123" ) ).isFalse();
        assertThat( docent1.getBijnamen() ).containsOnly( "test" );
    }

    @Test
    void meerdereDocentenKunnenTotDezelfdeCampusBehoren() {
        assertThat(campus1.getDocenten()).containsOnly(docent1, docent2);;
    }

    @Test
    void gelijkeDocentenHebbenHetzelfdeEmailAdres() {
        assertThat(docent1).isEqualTo(nogEensDocent1);
    }
    @Test
    void verschillendenDocentenHebbenEenVerschillendEmailAdres() {
        assertThat(docent1).isNotEqualTo(docent2);
    }
    @Test
    void eenDocentVerschiltVanNull() {
        assertThat(docent1).isNotEqualTo(null);
    }
    @Test
    void eenDocentVerschiltVanEenObjectDatGeenDocentIs() {
        assertThat(docent1).isNotEqualTo("");
    }
    @Test
    void gelijkeDocentenHebbenDezelfdeHashCode() {
        assertThat(docent1).hasSameHashCodeAs(nogEensDocent1);
    }

    @Test
    void docent1KomtVoorInCampus1() {
        assertThat(docent1.getCampus()).isEqualTo(campus1);
        assertThat(campus1.getDocenten()).contains(docent1);
    }
    @Test
    void docent1VerhuistNaarCampus2() {
        docent1.setCampus(campus2);
        assertThat(docent1.getCampus()).isEqualTo(campus2);
        assertThat(campus1.getDocenten()).containsOnly(docent2);
        assertThat(campus2.getDocenten()).containsOnly(docent1);
    }

    @Test
    void eenNullCampusInDeSetterMislukt() {
        assertThatNullPointerException().isThrownBy(()->docent1.setCampus(null));
    }

}
