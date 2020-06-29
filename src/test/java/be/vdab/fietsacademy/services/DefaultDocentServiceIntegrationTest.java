package be.vdab.fietsacademy.services;



import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(DefaultDocentService.class)

// used when importing bean from other package
@ComponentScan(
        value = "be.vdab.fietsacademy.repositories",
        resourcePattern = "JpaDocentRepository.class"
)
@Sql("/insertDocent.sql")
public class DefaultDocentServiceIntegrationTest
extends AbstractTransactionalJUnit4SpringContextTests {
    private final DefaultDocentService defaultDocentService;
    private final EntityManager entityManager;

    public DefaultDocentServiceIntegrationTest(
            DefaultDocentService defaultDocentService,
            EntityManager entityManager
    ) {
        this.defaultDocentService = defaultDocentService;
        this.entityManager = entityManager;
    }

    private long idVanTestMan() {
        return super.jdbcTemplate.queryForObject(
                "select id from docenten where voornaam='testM'", long.class
        );
    }

    @Test
    void opslag() {
        var id = idVanTestMan();
        defaultDocentService.opslag(
                id, BigDecimal.TEN
        );
        // force entitymanager to execute this change immediately so test can be performed (default: exec on commit)
        entityManager.flush();
        assertThat(
                super.jdbcTemplate.queryForObject(
                        "select wedde from docenten where id=?",
                        BigDecimal.class,
                        id
                )
        ).isEqualByComparingTo("1100");
    }
}
