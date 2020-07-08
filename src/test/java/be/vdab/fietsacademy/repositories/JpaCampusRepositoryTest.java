package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Adres;
import be.vdab.fietsacademy.domain.Campus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaCampusRepository.class)
@Sql("/insertCampus.sql")
public class JpaCampusRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    private static final String CAMPUSSEN = "CAMPUSSEN";
    private JpaCampusRepository jpaCampusRepository;

    public JpaCampusRepositoryTest(JpaCampusRepository jpaCampusRepository) {
        this.jpaCampusRepository = jpaCampusRepository;
    }

    private long idVanTestCampus() {
        return super.jdbcTemplate.queryForObject(
                "select id from campussen where naam='test'",
                Long.class
        );
    }

    @Test
    void findById(){
        var testCampus = jpaCampusRepository.findById( idVanTestCampus() ).get();
        assertThat( testCampus.getNaam() ).isEqualTo("test");
        assertThat( testCampus.getAdres().getStraat() ).isEqualTo("test");
    }

    @Test
    void findByOnbestaandeId(){
        assertThat( jpaCampusRepository.findById( -1 ) ).isNotPresent();
    }

    @Test
    void create(){
        var newCampus = new Campus( "newCampus", new Adres(
                "newAdres", "newAdres", "newAdres", "newAdres")
        );
        jpaCampusRepository.create( newCampus );
        assertThat(
                super.countRowsInTableWhere(
                        "CAMPUSSEN",
                        "id='" + newCampus.getId() + "'"
                )
        ).isOne();
    }

}
