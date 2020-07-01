package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Cursus;

import java.util.Optional;

public interface CursusRepository {

//    Optional<Cursus> findById(long id);
    // for table per concrete class inherintance - UUID > String type
Optional<Cursus> findById(String id);

    void create (Cursus cursus);

}
