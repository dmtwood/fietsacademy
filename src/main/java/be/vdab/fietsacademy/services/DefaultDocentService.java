package be.vdab.fietsacademy.services;

import be.vdab.fietsacademy.exceptions.DocentNietGevondenException;
import be.vdab.fietsacademy.repositories.DocentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
@Transactional
public class DefaultDocentService implements DocentService {

    private final DocentRepository docentRepository;

    public DefaultDocentService(DocentRepository docentRepository) {
        this.docentRepository = docentRepository;
    }



    @Override
    public void opslag(long id, BigDecimal percentage) {
//        throw new UnsupportedOperationException();
        docentRepository .findById( id )
                .orElseThrow(
                        () -> new DocentNietGevondenException()
                ) .opslag( percentage );
    }
}
