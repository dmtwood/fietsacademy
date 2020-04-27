package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Docent;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.print.Doc;
import java.util.Optional;

@Repository
public class JpaDocentRepository implements DocentRepository {
    private final EntityManager entityManager;

    public JpaDocentRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Docent> findById(long id) {
        return Optional
                .ofNullable(
                        entityManager
                                .find(
                                        Docent.class,
                                        id
                        )
                );
        //        throw new UnsupportedOperationException();
    }

    @Override
    public void create(Docent docent) {
        entityManager.persist(docent);
    }
}
