package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.GroepsCursus;
import be.vdab.fietsacademy.domain.IndividueleCursus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaCursusRepository.class)
@Sql("/insertCursus.sql")
public class JpaCursusRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String CURSUSSEN = "cursussen";
    private static final String GROEPS_CURSUSSEN = "groepscursussen";
    private static final String INDIVIDUELE_CURSUSSEN = "individuelecursussen";
    private static final LocalDate EEN_DATUM = LocalDate.of(2021,1,1);
    private final JpaCursusRepository cursusRepository;

    public JpaCursusRepositoryTest(JpaCursusRepository cursusRepository) {
        this.cursusRepository = cursusRepository;
    }

    private long idVanTestGroepCursus(){
        return super.jdbcTemplate.queryForObject(
                "select id from cursussen where naam='testGroep'",
                Long.class
        );
    }

    private long idVanTestIndividueelCursus(){
        return super.jdbcTemplate.queryForObject(
                "select id from cursussen where naam='testIndividueel'",
                long.class
        );
    }
    // designed  for single table inheritance
//    @Test
//    void findGroepCursusById() {
//        var optionalCursus = cursusRepository.findById( idVanTestGroepCursus() );
//        assertThat(
//                optionalCursus.get()
//                        .getNaam()
//        ).isEqualTo("testGroep");
//    }
//
//    @Test
//    void findIndividueelCursusById() {
//        var optionalCursus = cursusRepository.findById( idVanTestIndividueelCursus() );
//        assertThat(
//            optionalCursus.get()
//                    .getNaam()
//        ).isEqualTo("testIndividueel");
//    }

    @Test
    void findByOnbestaandeId() {
        assertThat( cursusRepository.findById( -1 ) )
                .isNotPresent();
    }

    @Test
    void createGroepsCursus(){
        var testCursus2 = new GroepsCursus("testGroep2", EEN_DATUM, EEN_DATUM);
        cursusRepository.create( testCursus2 );
        assertThat(
                super.countRowsInTableWhere(
                        CURSUSSEN,
                        "id='" + testCursus2.getId() + "'")
        ).isOne();
        // add this for table.joined inheritance
        assertThat(
                super.countRowsInTableWhere(
                        GROEPS_CURSUSSEN,
                        "id='" + testCursus2.getId() + "'")
        ).isOne();
    }

    @Test
    void createIndividueelCursus(){
        var testCursus3 = new IndividueleCursus("testIndividueel3", 7);
        cursusRepository.create( testCursus3 );
        assertThat(
                super.countRowsInTableWhere(
                        CURSUSSEN,
                        "id='" + testCursus3.getId() + "'")
        ).isOne();
        // add this for table.joined inheritance
        assertThat(
                super.countRowsInTableWhere(
                        INDIVIDUELE_CURSUSSEN,
                        "id='" + testCursus3.getId() + "'")
        ).isOne();
    }
}
