package be.vdab.fietsacademy.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

// @DataJpaTest
public class TelefoonNrTest {
    private TelefoonNr nummer1, nogEensNummer1, nummer2;

    @BeforeEach
    void beforeEach() {
        nummer1 = new TelefoonNr("1", false, "");
        nogEensNummer1 = new TelefoonNr("1", false, "");
        nummer2 = new TelefoonNr("2", false, "");
    }

    @Test
    void gelijkeTelefoonNrsHebbenHetzelfdeNummer() {
        assertThat( nummer1 ).isEqualTo( nogEensNummer1 );
    }

    @Test
    void verschillendeTelefoonNrsHebbenVerschillendeNummers() {
        assertThat( nummer1 ).isNotEqualTo( nummer2 );
    }

    // overbodig als equals overridden
    @Test
    void gelijkeTelefoonNrsHebbenHetzelfdeNummerBis() {
        assertThat( nummer1.getNummer() ).isEqualTo( nogEensNummer1.getNummer() );
    }

    // overbodig als equals overridden?
    @Test
    void verschillendeTelefoonNrsHebbenVerschillendeNummersBis() {
        assertThat( nummer1.getNummer() ).isNotEqualTo( nummer2.getNummer() );
    }

    @Test
    void eenTelefoonNrIsGeenNull() {
        assertThat( nummer1 ).isNotEqualTo( null );
    }

    @Test
    void eenTeleFoonNummerIsGeenAnderObject() {
        assertThat( nummer1 ).isNotEqualTo( "" );
    }

    @Test
    void gelijkeTelefoonNrsHebbenGelijkeHascodes() {
        assertThat( nummer1.hashCode() ).isEqualTo( nogEensNummer1.hashCode() );
    }

    



}
