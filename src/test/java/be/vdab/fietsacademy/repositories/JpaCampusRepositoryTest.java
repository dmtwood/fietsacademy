package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Adres;
import be.vdab.fietsacademy.domain.Campus;
import be.vdab.fietsacademy.domain.TelefoonNr;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@DataJpaTest
@Import(JpaCampusRepository.class)
@Sql("/insertCampus.sql")
@Sql("/insertDocent.sql")
public class JpaCampusRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    private static final String CAMPUSSEN = "CAMPUSSEN";
    private JpaCampusRepository jpaCampusRepository;

    public JpaCampusRepositoryTest(JpaCampusRepository jpaCampusRepository) {
        this.jpaCampusRepository = jpaCampusRepository;
    }

    private long idVanTestCampus() {
        return super.jdbcTemplate.queryForObject(
                "select id from campussen where naam='testc'",
                Long.class
        );
    }

    @Test
    void findById(){
        var testCampus = jpaCampusRepository.findById( idVanTestCampus() ).get();
        assertThat( testCampus.getNaam() ).isEqualTo("testc");
        assertThat( testCampus.getAdres().getStraat() ).isEqualTo("testc");
    }

    @Test
    void findByOnbestaandeId(){
        assertThat( jpaCampusRepository.findById( -1 ) ).isNotPresent();
    }

    @Test
    void create(){
        var newCampus = new Campus( "newCampus", new Adres(
                "newStraat", "newNr", "newPostcode", "newGemeente")
              //  , new TelefoonNr("a", false, "opmerk")
        );
        jpaCampusRepository.create( newCampus );
        assertThat(
                super.countRowsInTableWhere(
                        "CAMPUSSEN",
                        "id='" + newCampus.getId() + "'"
                )
        ).isOne();
    }



    @Test
    void telefoonNrsLezen() {
        assertThat(
                jpaCampusRepository.findById( idVanTestCampus() ).get().getTelefoonNrs()
        ).containsOnly(
                new TelefoonNr("1", false, "test")
        );
    }


    @Test
    void docentenLazyLoaded() {
        assertThat(jpaCampusRepository.findById(idVanTestCampus()).get().getDocenten())
.hasSize(2)
                .first().extracting(docent->docent.getVoornaam()).isEqualTo("testM");
    }



}
