package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Geslacht;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql("/insertDocent.sql")
@Import(JpaDocentRepository.class)
public class JpaDocentRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
private final JpaDocentRepository repository;

    public JpaDocentRepositoryTest(JpaDocentRepository repository) {
        this.repository = repository;
    }

    private long idVanTestMan() {
        return super.jdbcTemplate
                .queryForObject(
                "select id from docenten where voornaam = 'testM'",
                Long.class
        );
    }

    private long idVanTestVrouw(){
        return super.jdbcTemplate
                .queryForObject(
                        "'select id from docenten where voornaam = testV'",
                        Long.class
                );
    }

    @Test
    void findById() {
        assertThat(
                repository.findById(idVanTestMan())
                .get() .getVoornaam()
        ).isEqualTo(
                "testM"
        );
    }

    @Test
    void findByOnbestaandeId() {
        assertThat(
                repository.findById(-1)
        ).isNotPresent();
    }

    @Test
    void man(){
        assertThat(
                repository.findById( idVanTestMan() )
                .get() .getGeslacht()
        ).isEqualTo(
                Geslacht.MAN
        );
    }

    @Test
    void Vrouw(){
        assertThat(
                repository.findById( idVanTestVrouw() )
        ).isEqualTo(
                Geslacht.VROUW
        );
    }



}
