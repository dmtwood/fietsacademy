package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Adres;
import be.vdab.fietsacademy.domain.Campus;
import be.vdab.fietsacademy.domain.Docent;
import be.vdab.fietsacademy.domain.Geslacht;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql("/insertCampus.sql")
@Sql("/insertDocent.sql")
@Import(JpaDocentRepository.class)
public class JpaDocentRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
private final JpaDocentRepository repository;

    private Docent docent;
    private final EntityManager manager;
    private static final String DOCENTEN = "docenten";
    private Campus campus;

    JpaDocentRepositoryTest(JpaDocentRepository repository, EntityManager manager) {
        this.repository = repository;
        this.manager = manager;
    }

    @BeforeEach
    void beforeEach() {
        campus =new Campus("test", new Adres("newStraat", "newNr", "newPostcode", "newGemeente" ) );
        docent = new Docent("test",
                "test",
                BigDecimal.TEN,
                "test@test.be",
                Geslacht.MAN
                // commented for one to many, p. 62
                 ,   campus
        );
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
                        "select id from docenten where voornaam = 'testV'",
                        Long.class
                );
    }

    @Test
    void findById() {
        assertThat(
                repository.findById( idVanTestMan() )
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
    void geslachtMan(){
        assertThat(
                repository.findById( idVanTestMan() )
                .get() .getGeslacht()
        ).isEqualTo(
                Geslacht.MAN
        );
    }

    @Test
    void geslachtVrouw(){
        assertThat(
                repository.findById( idVanTestVrouw() )
                .get() .getGeslacht()
        ).isEqualTo(
                Geslacht.VROUW
        );
    }

    @Test
    void create(){
        manager.persist(campus);
        repository.create(docent);
        manager.flush();     //  for one to many, p. 62

        assertThat(
                docent.getId()
        ).isPositive();

        assertThat(
                super.countRowsInTableWhere(
                        DOCENTEN,
                        "id=" + docent.getId()
                )
        ).isOne();

        assertThat(
                super.jdbcTemplate.queryForObject(
                        "select campusid from docenten where id=?",
                        Long.class,
                        docent.getId()
                )
        ).isEqualTo(
                campus.getId()
        );


    }

    @Test
    void delete(){
        var id = idVanTestMan();
        repository.delete(id);
        manager.flush();
        assertThat(
                super.countRowsInTableWhere(
                        DOCENTEN,
                        "id=" + id
                )
        ).isZero();
    }

    @Test
    void findAll(){
        assertThat(
                repository.findAll()
        ).hasSize(
                super.countRowsInTable(DOCENTEN)
        ).extracting(
                docent -> docent.getWedde()
        ).isSorted();
    }

    @Test
    void findWeddeBetween(){
        var duizend = BigDecimal.valueOf(1_000);
        var tweeduizend = BigDecimal.valueOf(2_000);
        var docenten = repository.findByWeddeBetween(duizend, tweeduizend);
        assertThat(
                docenten
        ).hasSize(
                super.countRowsInTableWhere(
                        DOCENTEN, "wedde between 1000 and 2000"
                )
        ).allSatisfy(
                docent -> assertThat(
                        docent.getWedde()
                ).isBetween(
                        duizend, tweeduizend
                )
        );
    }

    @Test
    void findEmailAdresses(){
        assertThat(
                repository.findEmailAdressen()
        ).hasSize(
                super.jdbcTemplate.queryForObject(
                        "select count(emailadres) from docenten",
                        Integer.class
                )
        ).allSatisfy(
                adres -> adres.contains("@")
        );
    }

    @Test
    void findIdsAndEmailAdresses(){
        assertThat(
                repository.findIdsEnsEmailAdresssen()
        ).hasSize(
                super.countRowsInTable(DOCENTEN)
        );
    }

    @Test
    void findGrootsteWedde(){
        assertThat(
                repository.findGrootsteWedde()
        ).isEqualByComparingTo(
                super.jdbcTemplate.queryForObject(
                        "select max( wedde ) from docenten",
                        BigDecimal.class
                )
        );
    }


    @Test
    void aantalDocentenPerWedde(){
        var duizend = BigDecimal.valueOf(1_000);

        assertThat( repository.findAantalDocentenPerWedde() )

                .hasSize( super.jdbcTemplate.queryForObject(
                        "select count(distinct wedde) from docenten",
                        Integer.class) )

                .filteredOn( aantalPerWedde ->
                        aantalPerWedde.getWedde().compareTo(duizend) == 0)

                .allSatisfy( aantalPerWedde -> assertThat( aantalPerWedde.getAantal() )
                                                .isEqualTo(
                                                super.countRowsInTableWhere(DOCENTEN, "wedde=1000"))
                );
    }

    @Test
    void algemeneOpslag(){
        assertThat(
                repository.algemeneOpslag(BigDecimal.TEN)
        ).isEqualTo(
                super.countRowsInTable(DOCENTEN)
        );

        assertThat(
                super.jdbcTemplate.queryForObject(
                        "select wedde from docenten where id=?",
                        BigDecimal.class,
                        idVanTestMan()
                )
        ).isEqualByComparingTo("1100");
    }

    @Test
    void bijnamenLezen() {
        assertThat( repository.findById( idVanTestMan() ).get().getBijnamen()).containsOnly("test");
    }

    @Test
    void bijnaamToevoegen() {
        manager.persist(campus);
        repository.create( docent );
        docent.addBijnaam("test");
        manager.flush();
        assertThat(
                super.jdbcTemplate.queryForObject(
                        "select bijnaam from docentenbijnamen where docentid=?",
                        String.class,
                        docent.getId()
                )
        ).isEqualTo("test");
    }

    // commented for one to many, p. 62
    @Test
    void campusLazyLoaded() {
        var docent = repository.findById(idVanTestMan()).get();
        assertThat(docent.getCampus().getNaam()).isEqualTo("testc");
    }

}
