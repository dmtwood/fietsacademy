package be.vdab.fietsacademy.services;

import be.vdab.fietsacademy.domain.Docent;
import be.vdab.fietsacademy.domain.Geslacht;
import be.vdab.fietsacademy.exceptions.DocentNietGevondenException;
import be.vdab.fietsacademy.repositories.DocentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.security.Provider;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultDocentServiceTest {

    private DefaultDocentService docentService;

    @Mock
    private DocentRepository docentRepository;
    private Docent docent;

    @BeforeEach
    void beforeEach(){
        docent = new Docent(
                "test", "test", BigDecimal.valueOf(100), "test@test.be", Geslacht.MAN
        );
        docentService = new DefaultDocentService( docentRepository );
    }

    @Test
    void opslag() {
        when(
                docentRepository .findById( 1 )
        ) .thenReturn(
                Optional.of( docent )
        );
        docentService .opslag( 1, BigDecimal.TEN );
        assertThat(
                docent .getWedde()
        ) .isEqualByComparingTo( "110");
        verify( docentRepository )
                .findById( 1 );
    }

    @Test
    void opslagOnbestaandeDocent(){
        assertThatExceptionOfType(DocentNietGevondenException.class)
                .isThrownBy(
                        () -> docentService .opslag( -1, BigDecimal.TEN)
                );
        verify( docentRepository ) .findById( -1 );
    }

}
