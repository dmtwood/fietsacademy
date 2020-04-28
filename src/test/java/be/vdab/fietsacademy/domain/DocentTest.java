package be.vdab.fietsacademy.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.*;

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


}
