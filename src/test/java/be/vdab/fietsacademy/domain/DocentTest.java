package be.vdab.fietsacademy.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
class DocentTest {
    private static final BigDecimal WEDDE = BigDecimal.valueOf(2000);
    private Docent docent1;
    @BeforeEach
    void beforeEach() {
        docent1 = new Docent("test", "test", WEDDE, "test@test.be", Geslacht.MAN);
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
}
