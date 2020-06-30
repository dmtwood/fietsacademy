package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Docent;
import be.vdab.fietsacademy.queryresults.AantalDocentenPerWedde;
import be.vdab.fietsacademy.queryresults.IdEnEmailAdres;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.print.Doc;
import java.math.BigDecimal;
import java.util.List;
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
        entityManager
                .persist(
                        docent
                );
    }

    @Override
    public void delete(long id) {
        findById(id)
                .ifPresent(
//                        docent -> entityManager.remove(docent)
                        entityManager::remove
                );
    }

    @Override
    public List<Docent> findAll() {
        return entityManager.createQuery(
                "select d from Docent d order by d.wedde",
                Docent.class
        ).getResultList();
    }

    @Override
    public List<Docent> findByWeddeBetween(BigDecimal van, BigDecimal tot) {
        return entityManager
                .createNamedQuery("Docent.FindByWeddeBetween", Docent.class)

//                .createQuery(
//                "select d from Docent d where d.wedde between :van and :tot",
//                Docent.class
//        )
                .setParameter("van", van)
                .setParameter("tot", tot)
                .getResultList();
    }

    @Override
    public List<String> findEmailAdressen() {
        return entityManager.createQuery(
                "select d.emailAdres from Docent d",
                String.class
        ).getResultList();
    }

    @Override
    public List<IdEnEmailAdres> findIdsEnsEmailAdresssen() {
        return entityManager.createQuery(
                "select new be.vdab.fietsacademy.queryresults.IdEnEmailAdres(" +
                        "d.id, d.emailAdres) from Docent d",
                IdEnEmailAdres.class
        ).getResultList();
    }

    @Override
    public BigDecimal findGrootsteWedde() {
        return entityManager.createQuery(
                "select max (d.wedde) from Docent d",
                BigDecimal.class
        ).getSingleResult();
    }

    @Override
    public List<AantalDocentenPerWedde> findAantalDocentenPerWedde() {
        return entityManager.createQuery(
                "select new " +
                        "be.vdab.fietsacademy.queryresults.AantalDocentenPerWedde(d.wedde,count(d)) " +
                        "from Docent d group by d.wedde"
                , AantalDocentenPerWedde.class)
                .getResultList();
    }

    @Override
    public int algemeneOpslag(BigDecimal percentage) {
        var factor = BigDecimal.ONE.add(percentage.divide(BigDecimal.valueOf(100)));
        return entityManager.createNamedQuery("Docent.algemeneOpslag")
                .setParameter("factor", factor)
                .executeUpdate();
    }
}
